package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegionService {
private RegionRepository regionRepository;

public RegionDTO create(RegionDTO dto,Integer jwt){

    isValidRegion(dto);//todo

    RegionEntity entity = new RegionEntity();
    entity.setKey(dto.getKey());
    entity.setNameUz(dto.getUz());
    entity.setNameEn(dto.getEng());
    entity.setNameRu(dto.getRu());
    entity.setCreatedDate(LocalDateTime.now());
    entity.setVisible(true);
    regionRepository.save(entity);

    dto.setId(entity.getId());
    return dto;
}
    public void isValidRegion(RegionDTO dto) {
        // throw ... exp
    }
}
