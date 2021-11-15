package com.subscribe.platform.services.dto;

import com.subscribe.platform.subscribe.entity.Delivery;

import lombok.Data;

@Data
public class ReqDetailDeliveryDto {

	private long subscribeId;
	private boolean isComplete;
	private String serviceName;

	public ReqDetailDeliveryDto(Delivery delivery) {
		this.subscribeId = delivery.getSubscribe().getId();
		this.isComplete = delivery.isComplete();
		this.serviceName = delivery.getSubscribe().getServices().getName();
	}
}
