package com.subscribe.platform.services.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class ReqRegistReviewDto {

    @NotEmpty
    private String title;
    @Length(min = 10)
    private String content;
    private List<MultipartFile> imageFiles;
    @NotNull
    private Long serviceId;
}
