package com.example.repository;

import org.springframework.data.repository.CrudRepository;

public interface SavedArticleRepository extends CrudRepository {
    int deleteByProfileIdAndArticleId(Integer profileId, String articleId);
}
