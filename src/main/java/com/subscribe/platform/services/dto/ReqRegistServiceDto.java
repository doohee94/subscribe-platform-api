package com.subscribe.platform.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class ReqRegistServiceDto {

    @NotBlank
    private String serviceName;
    @NotBlank
    private String serviceCycle;
    @NotBlank
    private String availableDay;
    @NotBlank
    private String detailContents;

    @NotEmpty
    private List<ReqRegistServiceOptionDto> serviceOptions;
    @NotEmpty
    private List<ReqRegistServiceImageDto> serviceImages;
    @NotEmpty
    private List<ReqRegistCategoryDto> categories;

    public ReqRegistServiceDto(String serviceName, String serviceCycle, String availableDay, String detailContents, List<ReqRegistServiceOptionDto> serviceOptions, List<ReqRegistServiceImageDto> serviceImages, List<ReqRegistCategoryDto> categories) {
        this.serviceName = serviceName;
        this.serviceCycle = serviceCycle;
        this.availableDay = availableDay;
        this.detailContents = detailContents;
        this.serviceOptions = serviceOptions;
        this.serviceImages = serviceImages;
        this.categories = categories;
    }

}
