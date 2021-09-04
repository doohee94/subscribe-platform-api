package com.subscribe.platform.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class CreateServiceImageDto {

    private String imageType;
    private int imageSeq;
    private MultipartFile imageFile;

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setImageSeq(int imageSeq) {
        this.imageSeq = imageSeq;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
