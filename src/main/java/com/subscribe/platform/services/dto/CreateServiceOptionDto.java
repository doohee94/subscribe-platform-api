package com.subscribe.platform.services.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateServiceOptionDto {
    private String optionName;
    private int price;
    private int stock;
    private int maxCount;

    @Builder
    public CreateServiceOptionDto(String optionName, int price, int stock, int maxCount) {
        this.optionName = optionName;
        this.price = price;
        this.stock = stock;
        this.maxCount = maxCount;
    }
}
