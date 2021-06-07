package com.subscribe.platform.services.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "service_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceImage {

    @Id @GeneratedValue
    @Column(name = "service_image_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    private int imageSeq;   // 이미지 순서
    @Column(name = "image_name")
    private String name;
    private String fakeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Services services;

    public void setServices(Services services) {
        this.services = services;
    }

    @Builder
    public ServiceImage(ImageType imageType, int imageSeq, String name, String fakeName) {
        this.imageType = imageType;
        this.imageSeq = imageSeq;
        this.name = name;
        this.fakeName = fakeName;
    }
}
