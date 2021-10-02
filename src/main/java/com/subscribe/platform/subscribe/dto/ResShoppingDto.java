package com.subscribe.platform.subscribe.dto;

import com.subscribe.platform.services.entity.ServiceOption;
import com.subscribe.platform.subscribe.entity.Subscribe;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResShoppingDto {
    private Long subscribeId;
    private String serviceName;
    private String serviceImage;
    private List<ResSubsOptionDto> options;
    private String deliveryCycle;
    private String deliveryDay;

    @Builder
    public ResShoppingDto(String serviceName, String serviceImage, List<ServiceOption> serviceOptions, Subscribe subscribe){
        this.subscribeId = subscribe.getId();
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.options = serviceOptions.stream().map(
                option -> new ResSubsOptionDto(option.getName(), option.getPrice(),
                        subscribe.getPickedOptions().stream()
                                .filter(o -> o.getOptionId() == option.getId())
                                .map(o -> o.getQuantity())
                                .findAny().orElse(null))
        ).collect(Collectors.toList());
        this.deliveryCycle = subscribe.getDeliveryCycle() == null ? null : subscribe.getDeliveryCycle().toString();
        this.deliveryDay = subscribe.getDeliveryDay();
    }
}
