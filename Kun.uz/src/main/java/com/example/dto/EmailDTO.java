package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class EmailDTO {
    private Integer id;
    private String toEmail;
    private String title;
    private String content;
    private LocalDateTime createdDate;
}
