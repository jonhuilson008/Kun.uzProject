package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.Language;
import com.example.exps.AppBadRequestException;
import com.example.exps.RegionAlreadyExsistException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {

        if (dto.getId() != null) {
            throw new RegionAlreadyExsistException("Already exists key");
        }
        CategoryEntity entity = new CategoryEntity();
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
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }


    public List<CategoryDTO> getByLang(Language lang) {

        return null;
    }

    public boolean update(Integer id, CategoryDTO dto) {
        CategoryEntity entity = get(id);
        entity.setVisible(dto.getVisible());
        entity.setNameEn(dto.getEng());
        entity.setNameRu(dto.getRu());
        entity.setNameUz(dto.getUz());
        categoryRepository.save(entity);
        return true;
    }

    public CategoryEntity get(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("category not found: " + id);
        }
        return optional.get();
    }
    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new LinkedList<>();

        iterable.forEach(entity -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setEng(entity.getNameEn());
            dto.setRu(entity.getNameRu());
            dto.setUz(entity.getNameUz());
            dto.setVisible(entity.getVisible());
            dtoList.add(dto);
        });
        return dtoList;
    }


    public boolean delete(Integer id) {
        CategoryEntity entity = get(id);
        categoryRepository.delete(entity);
        return true;
    }
}
