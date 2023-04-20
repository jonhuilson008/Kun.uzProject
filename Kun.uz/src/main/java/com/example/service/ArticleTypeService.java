package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.RegionDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.enums.Language;
import com.example.exps.AppBadRequestException;
import com.example.exps.RegionAlreadyExsistException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {

        if (dto.getId() != null) {
            throw new RegionAlreadyExsistException("Already exists key");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
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
        articleTypeRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }


    public List<ArticleTypeDTO> getByLang(Language lang) {

        return null;
    }

    public boolean update(Integer id, ArticleTypeDTO dto) {
        ArticleTypeEntity entity = get(id);
        entity.setVisible(dto.getVisible());
        entity.setNameEn(dto.getEng());
        entity.setNameRu(dto.getRu());
        entity.setNameUz(dto.getUz());
        articleTypeRepository.save(entity);
        return true;
    }

    public ArticleTypeEntity get(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("ArticleType not found: " + id);
        }
        return optional.get();
    }
}
