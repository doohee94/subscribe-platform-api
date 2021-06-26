package com.subscribe.platform.services.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateServiceDto {

    private String serviceName;
    private String serviceCycle;
    private String availableDay;
    private List<CreateServiceOptionDto> serviceOptions;
    private List<CreateServiceImageDto> serviceImages;
    private List<CreateCategoryDto> categories;
}
