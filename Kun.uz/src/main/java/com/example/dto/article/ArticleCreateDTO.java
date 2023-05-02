package com.example.dto.article;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleCreateDTO {

    private String id;
    private String title;
    private String description;
    private String content;
    private String imageId;
    private Integer regionId;
    private Integer categoryId;


}