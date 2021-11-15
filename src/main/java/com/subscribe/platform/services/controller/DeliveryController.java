package com.subscribe.platform.services.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.dto.ReqDeliveryCountDto;
import com.subscribe.platform.services.dto.ReqDetailDeliveryDto;
import com.subscribe.platform.services.dto.ReqServiceOptionsDto;
import com.subscribe.platform.subscribe.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

	private final DeliveryService deliveryService;

	@GetMapping("/store/{id}/deliveries/calendar")
	public ListResponse<ReqDeliveryCountDto> getDeliveriesDateAndCount(@PathVariable long id, int month){
		return new ListResponse<>(deliveryService.getDeliveriesDateAndCount(id,month));
	}

	@GetMapping("/store/{id}/deliveries")
	public ListResponse<ReqDetailDeliveryDto> getDeliveries(@PathVariable long id, String date){
		return new ListResponse<>(deliveryService.getDeliveries(id,date));
	}

	@GetMapping("/service/{subscribeId}/options")
	public ReqServiceOptionsDto getDeliveryDetail(@PathVariable long subscribeId){
		return deliveryService.getDeliveryDetail(subscribeId);
	}

}
