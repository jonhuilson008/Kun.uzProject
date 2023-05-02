package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private String key;
    private String uz;
    private String ru;
    private String eng;
    private Boolean visible;
}
