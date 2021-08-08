package com.subscribe.platform.services.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.subscribe.platform.services.entity.ImageType;
import com.subscribe.platform.services.entity.Services;
import lombok.Data;
import lombok.ToString;

@Data
public class ResServiceListDto {

    private Long serviceId;
    private String serviceName;
    private String thumbnailImage;

    @QueryProjection
    public ResServiceListDto(Long serviceId, String serviceName, String thumbnailImage) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.thumbnailImage = thumbnailImage;
    }

    public ResServiceListDto(Services services, String filePath) {
        this.serviceId =services.getId();
        this.serviceName = services.getName();
        this.thumbnailImage = filePath+
                services.getServiceImages().stream()
//                .filter(t->t.getImageType() == ImageType.THUMBNAIL)
//                .filter(t->t.getImageSeq() == 1)
//                .map(t->t.getFakeName()+t.getExtensionName())
                .map(t->t.getFakeName()+t.getExtensionName())
                .findFirst()
                .orElse(null)
        ;
    }
}
