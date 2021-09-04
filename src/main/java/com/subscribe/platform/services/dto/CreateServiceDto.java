package com.subscribe.platform.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateServiceDto {

    private String serviceName;
    private String serviceCycle;
    private String availableDay;
    private String detailContents;
    private List<CreateServiceOptionDto> serviceOptions;
    private List<CreateServiceImageDto> serviceImages;
    private List<CreateCategoryDto> categories;

    public CreateServiceDto(String serviceName, String serviceCycle, String availableDay, String detailContents, List<CreateServiceOptionDto> serviceOptions, List<CreateServiceImageDto> serviceImages, List<CreateCategoryDto> categories) {
        this.serviceName = serviceName;
        this.serviceCycle = serviceCycle;
        this.availableDay = availableDay;
        this.detailContents = detailContents;
        this.serviceOptions = serviceOptions;
        this.serviceImages = serviceImages;
        this.categories = categories;
    }

}
