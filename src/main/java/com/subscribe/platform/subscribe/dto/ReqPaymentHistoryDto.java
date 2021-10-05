package com.subscribe.platform.subscribe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReqPaymentHistoryDto {
    private int startDate;
    private int endDate;
    private String serviceName;
}
