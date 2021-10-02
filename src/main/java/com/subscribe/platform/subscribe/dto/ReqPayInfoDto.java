package com.subscribe.platform.subscribe.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReqPayInfoDto {
    List<Long> subscribeIds;
    private String cardNo;
    private String creditCardCompany;
}
