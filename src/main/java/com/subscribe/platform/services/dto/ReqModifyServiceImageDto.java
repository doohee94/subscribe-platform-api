package com.subscribe.platform.services.dto;

import com.subscribe.platform.common.model.FileInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ReqModifyServiceImageDto {
    private Long serviceImageId;
    private String imageType;
    private int imageSeq;
    private MultipartFile imageFile;
    private FileInfo fileInfo;
}
