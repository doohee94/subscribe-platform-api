package com.subscribe.platform.subscribe.dto;

import com.subscribe.platform.common.utils.DateUtils;
import com.subscribe.platform.services.entity.ServiceCycle;
import com.subscribe.platform.subscribe.entity.Delivery;
import com.subscribe.platform.subscribe.entity.Subscribe;
import com.subscribe.platform.user.entity.Store;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryDto {

	private Subscribe subscribe;
	private Store store;
	private List<LocalDate> deliveryDate;

	public DeliveryDto(Subscribe subscribe, Store store) {
		this.subscribe = subscribe;
		this.store = store;
		this.deliveryDate = deliveryDate(subscribe.getDeliveryCycle(), subscribe.getDeliveryDay());
	}

	private List<LocalDate> deliveryDate(ServiceCycle deliveryCycle, String deliveryDay) {

		List<LocalDate> tempDates = new ArrayList<>();
		LocalDate today = LocalDate.now();

		if (deliveryCycle == ServiceCycle.MONTH) {

			//월 -> 배송우너하는날 이 만약에 결제일

			// 결제일 > 배송일 보다 크면 -> 다음달 부터 배송
			// 결제일 < 배송이 작 이번달
			int dDay = Integer.parseInt(deliveryDay);

			if (today.getDayOfMonth() > dDay) {
				tempDates.add(today.plusMonths(1L));
			} else if (today.getDayOfMonth() < dDay) {
				tempDates.add(LocalDate.of(today.getYear(), today.getMonth(), dDay));
			}

		} else if (deliveryCycle == ServiceCycle.WEEK) {

			// 프론트에서

			DayOfWeek week = DateUtils.convertStringToDayOfWeek(deliveryDay);

			LocalDate tempDate = today;

			// 4주 배송
			for (int i = 0; i < 4; i++) {
				tempDate = tempDate.with(TemporalAdjusters.next(week));
				tempDates.add(tempDate);
			}
		}

		return tempDates.stream()
			.map(DateUtils::calculateHolyDay)
			.collect(Collectors.toList());

	}


	public List<Delivery> toEntities(){
		return this.deliveryDate.stream()
			.map(t->Delivery.builder()
				.deliveryDate(t)
				.store(this.store)
				.subscribe(this.subscribe)
				.build())
			.collect(Collectors.toList());

	}


}
