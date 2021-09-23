package com.subscribe.platform.subscribe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResSubsOptionDto {
    private String optionName;
    private int price;
    private int quantity;

    public ResSubsOptionDto(String optionName, int price, int quantity) {
        this.optionName = optionName;
        this.price = price;
        this.quantity = quantity;
    }
}
