package com.subscribe.platform.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ReqRegistServiceImageDto {

    @NotBlank
    private String imageType;
    @NotBlank
    private int imageSeq;
    @NotEmpty
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
