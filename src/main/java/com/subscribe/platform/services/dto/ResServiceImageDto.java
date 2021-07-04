package com.subscribe.platform.services.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ResServiceImageDto {
    private String imageType;
    private int imageSeq;
    private String imageUrl;

    @Builder
    public ResServiceImageDto(String imageType, int imageSeq, String imageUrl) {
        this.imageType = imageType;
        this.imageSeq = imageSeq;
        this.imageUrl = imageUrl;
    }
}
