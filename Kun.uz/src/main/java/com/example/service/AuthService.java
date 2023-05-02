package com.example.service;

import com.example.dto.AuthDTO;
import com.example.dto.AuthResponseDTO;
import com.example.dto.register.RegistrationDTO;
import com.example.dto.register.RegistrationResponseDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatusEnum;
import com.example.enums.LanguageEnum;
import com.example.enums.ProfileRoleEnum;
import com.example.exps.AppBadRequestException;
import com.example.exps.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private  MailSenderService mailSenderService;



    public AuthResponseDTO login(AuthDTO dto, LanguageEnum lang) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisible(
                dto.getEmail(),
                MD5Util.getMd5Hash(dto.getPassword()),
                true);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email or password incorrect");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatusEnum.ACTIVE)) {
            throw new AppBadRequestException("Wrong status");
        }
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return responseDTO;
    }
    public RegistrationResponseDTO registration(RegistrationDTO dto) {
        // check -?
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ItemNotFoundException("Email already exists mazgi.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(ProfileRoleEnum.USER);
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setStatus(GeneralStatusEnum.REGISTER);
        // send email
        mailSenderService.sendRegistrationEmail(dto.getEmail());
        // save
        profileRepository.save(entity);
        String s = "Verification link was send to email: " + dto.getEmail();
        return new RegistrationResponseDTO(s);
    }
    public RegistrationResponseDTO emailVerification(String jwt) {
        // asjkdhaksdh.daskhdkashkdja
        String email = JwtUtil.decodeEmailVerification(jwt);
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email not found.");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatusEnum.REGISTER)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(GeneralStatusEnum.ACTIVE);
        profileRepository.save(entity);
        return new RegistrationResponseDTO("Registration Done");
    }

    public String verification(String jwt) {
        return null;
    }
}
