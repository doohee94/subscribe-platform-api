package com.subscribe.platform.subscribe.dto;

import lombok.Data;

@Data
public class ReqCancelSubscribeDto {
    private Long subscribeId;
    private Long cancelReasonId;
    private String cancelReasonEtc;
}
