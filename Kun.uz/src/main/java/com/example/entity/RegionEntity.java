package com.example.entity;

import com.example.enums.GeneralStatus;
import com.example.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "region")
@Setter
@Getter
public class RegionEntity {
    //        id,key,name_uz, name_ru, name_en,visible,created_date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "key")
    private String key;
    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_eng")
    private String nameEn;
    @Column(name = "prt_id")
    private Integer prtId;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

}
