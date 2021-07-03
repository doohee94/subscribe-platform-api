package com.subscribe.platform.services.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResStoreServiceDto {

    private Long serviceId;
    private String serviceName;
    private String serviceCycle;
    private String availableDay;
    private String detailContents;
    private List<CreateServiceOptionDto> seviceOptions;
    private List<ResServiceImageDto> serviceImages;
    private List<ResCategoryDto> categories;
}
