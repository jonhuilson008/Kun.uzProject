package com.example.entity;

import com.example.enums.ArticleStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Setter
@Getter
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    @NonNull
    @Size(min = 10, max = 225, message = "Title must be between 10 and 225 characters")
    private String title;
    @Column
    private String description;
    @Column
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "image_id")
    private String imageId;
    @JoinColumn(name = "image_id", updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId ;

    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @Column(name = "category_id")
    private Integer categoryId;
    @JoinColumn(name = "category_id",updatable = false,insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;
    @Column(name = "moderator_id")
    private Integer moderatorId;
    @JoinColumn(name = "moderator_id",updatable = false,insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity moderator;
    @Column(name = "publisher_id")
    private Integer publisherId;
    @JoinColumn(name = "publisher_id",updatable = false,insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity publisher;
    @Enumerated(EnumType.STRING)
    private ArticleStatusEnum status = ArticleStatusEnum.NOT_PUBLISHED;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column
    private Boolean visible = Boolean.TRUE;
    @Column(name = "view_count")
    private Integer viewCount = 0;
    @Column(name = "prt_id")
    private Integer prtId;
}
