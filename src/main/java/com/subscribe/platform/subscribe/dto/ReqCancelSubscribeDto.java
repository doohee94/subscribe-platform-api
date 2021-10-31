package com.subscribe.platform.subscribe.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReqCancelSubscribeDto {

    @NotNull
    private Long subscribeId;
    @NotNull
    private Long cancelReasonId;
    private String cancelReasonEtc;
}
