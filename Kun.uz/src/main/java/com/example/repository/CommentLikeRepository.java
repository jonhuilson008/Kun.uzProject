package com.example.repository;

import com.example.entity.CommentEntity;
import com.example.entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {
    CommentLikeEntity findByProfileIdAndCommentId(Integer profileId, Integer commentId);

    void deleted(Integer profileId, Integer commentId);
}
