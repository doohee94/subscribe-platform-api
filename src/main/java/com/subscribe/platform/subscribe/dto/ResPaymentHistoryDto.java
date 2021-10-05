package com.subscribe.platform.subscribe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ResPaymentHistoryDto {
    private List<String> serviceName;
    private String creditCardCompany;
    private String paidCardNo;
    private int payPrice;
    private String payDate;
}
