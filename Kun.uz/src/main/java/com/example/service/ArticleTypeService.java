package com.example.service;

import com.example.dto.article.ArticleTypeDTO;


import com.example.entity.ArticleTypeEntity;
import com.example.enums.LanguageEnum;
import com.example.exps.AppBadRequestException;
import com.example.exps.RegionAlreadyExsistException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
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


    public List<ArticleTypeDTO> getByLang(LanguageEnum lang) {

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
            throw new AppBadRequestException("region not found: " + id);
        }
        return optional.get();
    }
    public List<ArticleTypeDTO> getAll() {
        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAll();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();

        iterable.forEach(entity -> {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setEng(entity.getNameEn());
            dto.setRu(entity.getNameRu());
            dto.setUz(entity.getNameUz());
            dto.setVisible(entity.getVisible());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public boolean delete(Integer id) {
        ArticleTypeEntity entity = get(id);
        articleTypeRepository.delete(entity);
        return true;
    }

}
