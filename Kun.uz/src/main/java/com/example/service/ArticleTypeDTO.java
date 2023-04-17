package com.example.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleTypeDTO {
    private Integer id;
    private String key;
    private String uz;
    private String ru;
    private String eng;
    private Boolean visible;
}
