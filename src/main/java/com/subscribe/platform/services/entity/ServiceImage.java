package com.subscribe.platform.services.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.services.dto.ReqModifyServiceImageDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "service_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_image_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    private int imageSeq;   // 이미지 순서
    @Column(name = "image_name")
    private String name;
    private String fakeName;
    private String extensionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Services services;

    public void setServices(Services services) {
        this.services = services;
    }

    @Builder
    public ServiceImage(Long id, ImageType imageType, int imageSeq, String name, String fakeName, String extensionName) {
        this.id = id;
        this.imageType = imageType;
        this.imageSeq = imageSeq;
        this.name = name;
        this.fakeName = fakeName;
        this.extensionName = extensionName;
    }

    public void updateServiceImage(ReqModifyServiceImageDto imageDto){
        this.imageType = "THUMBNAIL".equals(imageDto.getImageType()) ? ImageType.THUMBNAIL : ImageType.DETAIL;
        this.imageSeq = imageDto.getImageSeq();
        this.name = imageDto.getFileInfo().getOriginName();
        this.fakeName = imageDto.getFileInfo().getFakeName();
        this.extensionName = imageDto.getFileInfo().getExtensionName();
    }
}
