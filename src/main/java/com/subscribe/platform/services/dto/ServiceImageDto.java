package com.subscribe.platform.services.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ServiceImageDto {

    private String imageType;
    private int imageSeq;
    private String imageName;
    private String fakeName;

    private MultipartFile imageFile;
}
