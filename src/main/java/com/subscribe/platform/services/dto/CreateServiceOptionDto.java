package com.subscribe.platform.services.dto;

import lombok.Data;

@Data
public class CreateServiceOptionDto {
    private String optionName;
    private int price;
    private int stock;
    private int maxCount;
}
