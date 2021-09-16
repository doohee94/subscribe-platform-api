package com.subscribe.platform.subscribe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResSubsOptionDto {
    private String optionName;
    private int price;

    public ResSubsOptionDto(String optionName, int price) {
        this.optionName = optionName;
        this.price = price;
    }
}
