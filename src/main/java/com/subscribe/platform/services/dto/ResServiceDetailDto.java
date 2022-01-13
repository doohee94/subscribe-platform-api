package com.subscribe.platform.services.dto;

import com.subscribe.platform.common.model.SelectBox;
import com.subscribe.platform.services.entity.ServiceImage;
import com.subscribe.platform.services.entity.Services;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResServiceDetailDto {

    private Long serviceId;
    private String serviceName;
    private List<SelectBox<String>> serviceCycle;
    private List<SelectBox<String>> availableDay;
    private String detailContents;
    private List<ReqRegistServiceOptionDto> serviceOptions;
    private List<ResServiceImageDto> serviceImages;


    public ResServiceDetailDto(Services serviceDetail) {
        this.serviceId = serviceDetail.getId();
        this.serviceName = serviceDetail.getName();

        this.serviceCycle = new ArrayList<>();
        serviceCycle.add(new SelectBox<>(serviceDetail.getServiceCycle().name()));

        this.availableDay = new ArrayList<>();
        availableDay.add(new SelectBox<>(serviceDetail.getAvailableDay()));

        this.detailContents = serviceDetail.getDetailContents();

        this.serviceOptions = serviceDetail.getServiceOptions().stream()
                .map(ReqRegistServiceOptionDto::new)
                .collect(Collectors.toList());

//        this.serviceImages = serviceDetail.getServiceImages().stream()
//                .map(image -> image.getFakeName() + image.getExtensionName())
//                .collect(Collectors.toList());

        this.serviceImages = serviceDetail.getServiceImages().stream()
                .map(
                        images -> ResServiceImageDto.builder()
                                .imageType(images.getImageType().toString())
                                .imageSeq(images.getImageSeq())
                                .imageUrl(images.getFakeName() + images.getExtensionName())
                                .build()
                ).collect(Collectors.toList());

    }
}
