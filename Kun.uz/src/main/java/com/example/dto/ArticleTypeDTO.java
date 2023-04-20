package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleTypeDTO {
    private Integer id;
    private String uz;
    private String ru;
    private String eng;
    private Boolean visible;
}
