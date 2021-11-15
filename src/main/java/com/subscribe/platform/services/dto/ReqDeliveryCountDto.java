package com.subscribe.platform.services.dto;

import java.time.LocalDate;
import java.util.Map;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReqDeliveryCountDto {

	private String date;
	private Long count;

	public ReqDeliveryCountDto(Map.Entry<LocalDate, Long> map) {
		this.date = map.getKey().toString();
		this.count = map.getValue();
	}
}
