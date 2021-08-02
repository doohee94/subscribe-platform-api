package com.subscribe.platform.services.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateServiceOptionDto{
    private Long serviceOptionId;
    private String optionName;
    private int price;
    private int stock;
    private int maxCount;
}
