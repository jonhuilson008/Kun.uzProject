package com.example.dto.article;

import com.example.enums.ArticleStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ArticleDTO {

    private UUID id;
    private String title;
    private String description;
    private String content;
    private Integer shared_count;
    private Integer image_id;
    private Integer region_id;
    private Integer category_id;
    private Integer moderator_id;
    private Integer publisher_id;
    private ArticleStatusEnum status;
    private Integer view_count;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime published_date = LocalDateTime.now();


}
