package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exps.RegionAlreadyExsistException;
import com.example.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegionService {
private RegionRepository regionRepository;

public RegionDTO create(RegionDTO dto){

    isValidRegion(dto);

    RegionEntity entity = new RegionEntity();
    entity.setNameRu(dto.getRu());
    entity.setNameUz(dto.getUz());
    entity.setNameEn(dto.getEng());
    entity.setVisible(dto.getVisible());
    entity.setCreatedDate(LocalDateTime.now());
    entity.setVisible(true);

    regionRepository.save(entity);
    dto.setId(entity.getId());
    return dto;
}
    public void isValidRegion(RegionDTO dto) {
        RegionEntity byKey = regionRepository.findByKey(dto.getKey());
        if (byKey != null) {
            throw new RegionAlreadyExsistException("Already exists key");
        }
    }

    public List<RegionDTO> getByLang(Language lang) {

        return null;
    }
}
