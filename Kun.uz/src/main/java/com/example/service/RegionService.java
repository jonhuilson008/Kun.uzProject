package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.LanguageEnum;
import com.example.exps.AppBadRequestException;
import com.example.exps.RegionAlreadyExsistException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {

        if (dto.getId() != null) {
            throw new RegionAlreadyExsistException("Already exists key");
        }
        RegionEntity entity = new RegionEntity();
        entity.setNameRu(dto.getRu());
        entity.setNameUz(dto.getUz());
        entity.setNameEn(dto.getEng());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);

        if (dto.getEng() == null || dto.getEng().isBlank()) {
            throw new AppBadRequestException("Where is English version?");
        }
        if (dto.getRu() == null || dto.getRu().isBlank()) {
            throw new AppBadRequestException("Where is Russian version?");
        }
        if (dto.getUz() == null || dto.getUz().isBlank()) {
            throw new AppBadRequestException("Where is Uzbek version?");
        }
        if (dto.getVisible() == null) {
            throw new AppBadRequestException("Please fill visible icon ?");
        }
        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public boolean update(Integer id, RegionDTO dto) {
        RegionEntity entity = get(id);
        entity.setVisible(dto.getVisible());
        entity.setNameEn(dto.getEng());
        entity.setNameRu(dto.getRu());
        entity.setNameUz(dto.getUz());
        regionRepository.save(entity);
        return true;
    }

    public RegionEntity get(Integer id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("region not found: " + id);
        }
        return optional.get();
    }
    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();

        iterable.forEach(entity -> {
            RegionDTO dto = new RegionDTO();
           dto.setEng(entity.getNameEn());
           dto.setRu(entity.getNameRu());
           dto.setUz(entity.getNameUz());
           dto.setVisible(entity.getVisible());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public boolean delete(Integer id) {
        RegionEntity entity = get(id);
        regionRepository.delete(entity);
        return true;
    }

    public List<RegionDTO> getByLang(LanguageEnum lang) {
        List<RegionEntity> entityList = regionRepository.findAllByVisibleTrue();
        List<RegionDTO> dtoList = new ArrayList<>();
        for (RegionEntity entity : entityList) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            switch (lang) {
                case name_uz -> dto.setUz(entity.getNameUz());
                case name_en -> dto.setEng(entity.getNameRu());
                case name_ru -> dto.setRu(entity.getNameEn());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Integer getById(Integer regionId, LanguageEnum language) {
        return null;
    }

    public RegionEntity getById2(Integer id) {
        return null;
    }
}
