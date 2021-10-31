package com.subscribe.platform.services.dto;

import com.subscribe.platform.services.entity.ServiceOption;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ReqRegistServiceOptionDto {

    @NotBlank
    private String optionName;
    private int price;
    private int stock;
    private int maxCount;

    @Builder
    public ReqRegistServiceOptionDto(String optionName, int price, int stock, int maxCount) {
        this.optionName = optionName;
        this.price = price;
        this.stock = stock;
        this.maxCount = maxCount;
    }

    public CreateServiceOptionDto(ServiceOption serviceOption) {
        this(serviceOption.getName(),
                serviceOption.getPrice(),
                serviceOption.getStock(),
                serviceOption.getMaxCount());
    }
}
