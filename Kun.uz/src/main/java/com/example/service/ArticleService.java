package com.example.service;

import com.example.dto.ArticleRequestDTO;
import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatusEnum;
import com.example.enums.LanguageEnum;
import com.example.exps.ItemNotFoundException;
import com.example.mapper.IArticleShortInfoMapper;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private  ProfileService profileService;



    public ArticleRequestDTO create(ArticleRequestDTO dto, Integer moderId) {
        // check
        ProfileEntity moderator = profileService.get(moderId);
        RegionEntity region = regionService.get(dto.getRegionId());
        CategoryEntity category = categoryService.get(dto.getCategoryId());

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(moderId);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setImageId(String.valueOf(dto.getAttachId()));
        // type
        articleRepository.save(entity);
        return dto;
    }

    private ArticleEntity toEntity(ArticleCreateDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId((dto.getImageId()));
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        return entity;
    }

//    public int update(ArticleDTO dto, String id) {
//        get(id);
//        return articleRepository.update(dto.getStatus(), dto.getTitle(), dto.getDescription(),
//                dto.getContent(), dto.getRegion_id(), dto.getCategory_id(), id);
//    }

    public Boolean delete(String id) {
        get(id);
        int i = articleRepository.deleteVisible(id);
        return i == 1;
    }

    private void get(String id) {
        articleRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Not Found");
        });
    }

    private ArticleEntity getEntity(String id) {
        return  articleRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Not Found");
        });

    }

    public Boolean status(String id, ArticleStatusEnum status) {
        get(id);
        int i = articleRepository.status(id, status);
        return i == 1;
    }

    public List<ArticleDTO> lastFive(String status) {
        List<ArticleEntity> lastFive = articleRepository.lastFive(status);
        List<ArticleDTO> dto = new ArrayList<>();
        for (ArticleEntity entity : lastFive) {
            ArticleDTO articleDTO = toDTO(entity);
            dto.add(articleDTO);

        }
        return dto;
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublished_date(entity.getPublishedDate());

        return dto;
    }

    public List<IArticleShortInfoMapper> lastEight(List<String> idList) {
        return articleRepository
                .getTop8Article(ArticleStatusEnum.PUBLISHED.name(), idList);
    }

    public ArticleDTO getById(String id, LanguageEnum language) {
        Optional<ArticleEntity> optional = articleRepository.findByIdAndStatusAndVisibleTrue(id, ArticleStatusEnum.PUBLISHED);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Article not Found");
        }
        ArticleEntity entity = optional.get();
        ArticleDTO dto = new ArticleDTO();
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublished_date(entity.getPublishedDate());
        dto.setView_count(entity.getViewCount());
        dto.setShared_count(entity.getSharedCount());
        dto.setRegion_id(regionService.getById(entity.getRegionId(), language));
        dto.setCategory_id(categoryService.get1(entity.getCategoryId(), language));

        return dto;
    }

    public List<IArticleShortInfoMapper> lastFour(String id) {
        return articleRepository.getLastFour(id);
    }

    public List<IArticleShortInfoMapper> mostReads() {
        return articleRepository.mostReads();
    }



    public Page<IArticleShortInfoMapper> getListRegionPage(Integer page, Integer size, Integer id) {
        RegionEntity regionEntity = regionService.getById2(id);
        Pageable paging = PageRequest.of(page, size);
        Page<IArticleShortInfoMapper> page1 = articleRepository.findAllByKey(paging, regionEntity.getId());
        List<IArticleShortInfoMapper> list = page1.getContent();
        return new PageImpl<>(list, paging, page1.getTotalElements());
    }

    public List<IArticleShortInfoMapper> getFiveCategory(Integer id) {
        CategoryEntity category = categoryService.get(id);
       return articleRepository.getByCategoryId(category.getId());
    }


    public Page<IArticleShortInfoMapper> getListCategoryPage(Integer page, Integer size, Integer id) {
        CategoryEntity categoryEntity = categoryService.get(id);
        Pageable paging = PageRequest.of(page, size);
        Page<IArticleShortInfoMapper> page1 = articleRepository.findAllByKeyCategory(paging, categoryEntity.getId());
        List<IArticleShortInfoMapper> list = page1.getContent();
        return new PageImpl<>(list, paging, page1.getTotalElements());
    }

    public long viewCount(String id) {
         articleRepository.viewCount(id);
        return articleRepository.viewCountReturn(id);
    }

    public long shareCount(String articleId) {
        articleRepository.shareCount(articleId);
        return articleRepository.shareCountReturn(articleId);
    }

    public List<IArticleShortInfoMapper> getFiveRegion(String id) {
        return null;
    }

    public int update(ArticleDTO dto, String id) {
        return 0;
    }

    public ArticleRequestDTO create(ArticleRequestDTO dto) {
        return  null;
    }
}
