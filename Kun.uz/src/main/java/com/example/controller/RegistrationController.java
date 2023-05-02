package com.example.controller;

import com.example.dto.register.RegistrationDTO;
import com.example.dto.register.RegistrationResponseDTO;
import com.example.service.AuthService;
import com.example.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    @Autowired
    private AuthService authService;
    @Autowired
    private MailSenderService mailSenderService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }
}
