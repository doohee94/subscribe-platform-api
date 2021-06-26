package com.subscribe.platform.services.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateServiceImageDto {

    private String imageType;
    private int imageSeq;
    private MultipartFile imageFile;
}
