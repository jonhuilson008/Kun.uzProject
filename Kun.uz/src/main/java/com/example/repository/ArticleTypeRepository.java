package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity,Integer> {

}
