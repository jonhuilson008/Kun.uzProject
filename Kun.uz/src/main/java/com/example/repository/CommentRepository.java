package com.example.repository;

import com.example.entity.CommentEntity;
import com.example.mapper.CommentMapperShort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity,Integer> {
    int update(String content, String articleId);

    void deleteAdmin(Integer commentId);

    Page<CommentMapperShort> getPageList(Pageable paging);

    List<CommentMapperShort> repliedList(Integer commentId);
}
