package com.subscribe.platform.subscribe.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class ReqPaymentHistoryDto {

    @NotNull
    private int startDate;
    @NotNull
    private int endDate;
    private String serviceName;
}
