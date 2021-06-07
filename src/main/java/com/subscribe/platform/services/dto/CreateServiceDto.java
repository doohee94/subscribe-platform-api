package com.subscribe.platform.services.dto;

import com.subscribe.platform.services.entity.ServiceCycle;
import lombok.Data;

import java.util.List;

@Data
public class CreateServiceDto {

    private String serviceName;
    private String serviceCycle;
    private String availableDay;
    private List<ServiceOptionDto> serviceOptions;
    private List<ServiceImageDto> serviceImages;
    private List<CategoryDto> serviceCategories;
}
