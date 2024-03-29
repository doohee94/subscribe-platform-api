package com.subscribe.platform.services.dto;

import com.subscribe.platform.services.entity.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResStoreServiceDto {

    private Long serviceId;
    private String serviceName;
    private String serviceCycle;
    private String availableDay;
    private String detailContents;
    private List<ReqRegistServiceOptionDto> serviceOptions;
    private List<ResServiceImageDto> serviceImages;
    private List<ResCategoryDto> categories;

    public ResStoreServiceDto(Services services, List<ResCategoryDto> categoryDtos){
        this.serviceId = services.getId();
        this.serviceName = services.getName();
        this.serviceCycle = services.getServiceCycle().toString();
        this.availableDay = services.getAvailableDay();
        this.detailContents = services.getDetailContents();

        this.serviceOptions = services.getServiceOptions().stream()
                .map(o -> ReqRegistServiceOptionDto.builder()
                        .optionName(o.getName())
                        .price(o.getPrice())
                        .stock(o.getStock())
                        .maxCount(o.getMaxCount())
                        .build()
                ).collect(Collectors.toList());

        this.serviceImages = services.getServiceImages().stream()
                .map(o -> ResServiceImageDto.builder()
                        .imageType(o.getImageType().toString())
                        .imageSeq(o.getImageSeq())
                        .imageUrl(o.getFakeName()+o.getExtensionName())
                        .build()
                ).collect(Collectors.toList());

        this.categories = categoryDtos;
    }
}
