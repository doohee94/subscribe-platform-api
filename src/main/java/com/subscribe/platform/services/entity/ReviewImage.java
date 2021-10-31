package com.subscribe.platform.services.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "review_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    private String name;
    private String fakeName;
    private String extensionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public ReviewImage(String name, String fakeName, String extensionName){
        this.name = name;
        this.fakeName = fakeName;
        this.extensionName = extensionName;
    }

    public void setReview(Review review){
        this.review = review;
    }
}
