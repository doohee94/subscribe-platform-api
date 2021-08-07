package com.subscribe.platform.services.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateServiceDto {
    private Long serviceId;
    private String serviceName;
    private String serviceCycle;
    private String availableDay;
    private String detailContents;

    private List<UpdateServiceOptionDto> serviceOptions;
    private List<UpdateServiceImageDto> serviceImages;
    private List<UpdateServiceCategoryDto> categories;
}
