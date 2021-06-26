package com.subscribe.platform.services.dto;

import com.subscribe.platform.services.entity.ImageType;
import com.subscribe.platform.services.entity.Services;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResServiceListDto {

    private Long serviceId;
    private String serviceName;
    private String thumbnailImage;

    public ResServiceListDto(Services services) {
        this.serviceId =services.getId();
        this.serviceName = services.getName();
        this.thumbnailImage = "C:\\Users\\BMJ\\IdeaProjects\\subscribe-platform-api\\src\\main\\resources\\images\\"+
                services.getServiceImages().stream()
                .filter(t->t.getImageType() == ImageType.THUMBNAIL)
                .filter(t->t.getImageSeq() == 1)
                .map(t->t.getFakeName()+t.getExtensionName())
                .findFirst()
                .orElse(null)
        ;
    }
}
