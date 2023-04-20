package com.example.repository;

import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionEntity,Integer> {


    List<RegionEntity> findAllByVisibleTrue();
}
