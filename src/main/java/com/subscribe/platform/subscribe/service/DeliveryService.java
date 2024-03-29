package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.common.utils.DateUtils;
import com.subscribe.platform.services.dto.DateType;
import com.subscribe.platform.services.dto.ReqDeliveryCountDto;
import com.subscribe.platform.services.dto.ReqDetailDeliveryDto;
import com.subscribe.platform.services.dto.ReqServiceOptionsDto;
import com.subscribe.platform.services.entity.ServiceOption;
import com.subscribe.platform.services.repository.ServiceOptionRepository;
import com.subscribe.platform.subscribe.entity.Delivery;
import com.subscribe.platform.subscribe.entity.PickedOption;
import com.subscribe.platform.subscribe.repository.DeliveryRepository;
import com.subscribe.platform.subscribe.repository.PickedOptionRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final PickedOptionRepository pickedOptionRepository;
	private final ServiceOptionRepository serviceOptionRepository;

	public void save(List<Delivery> deliveries) {
		deliveryRepository.saveAll(deliveries);
	}

	public List<ReqDeliveryCountDto> getDeliveriesDateAndCount(long storeId, Integer month) {

		List<Delivery> deliveries = deliveryRepository.findByStore_Id(storeId);

		if(month == null){
			month = LocalDate.now().getMonthValue();
		}

		Integer finalMonth = month;
		return deliveries.stream().filter(t -> t.getDeliveryDate().getMonthValue() == finalMonth)
			.collect(Collectors.groupingBy(Delivery::getDeliveryDate, Collectors.counting()))
			.entrySet().stream()
			.map(ReqDeliveryCountDto::new)
			.collect(Collectors.toList());

	}

	public List<ReqDetailDeliveryDto> getDeliveries(long id, String date) {

		return  deliveryRepository.findByStoreIdAndDeliveryDate(id, DateUtils.convertStringToLocalDate(date))
			.stream()
			.map(ReqDetailDeliveryDto::new)
			.collect(Collectors.toList());

	}

	public List<ReqDetailDeliveryDto> getDeliveries(long id, DateType dateType, String date) {

		if(dateType == DateType.DAY){
			return  deliveryRepository.findByStoreIdAndDeliveryDate(id, DateUtils.convertStringToLocalDate(date))
					.stream()
					.map(ReqDetailDeliveryDto::new)
					.collect(Collectors.toList());
		}else{

			LocalDate startDay = dateType.startDay(date);
			LocalDate endDay = dateType.endDay(date);
			return deliveryRepository.findByStoreIdAndDeliveryDateBetween(id,startDay,endDay )
					.stream()
					.map(ReqDetailDeliveryDto::new)
					.collect(Collectors.toList());

		}
	}

	public ReqServiceOptionsDto getDeliveryDetail(long subscribeId) {

		List<PickedOption> pickedOptions = pickedOptionRepository.findBySubscribe_Id(subscribeId);
		List<Long> serviceOptionsId = pickedOptions.stream()
			.map(PickedOption::getOptionId)
			.collect(Collectors.toList());
		List<ServiceOption> serviceOptions = serviceOptionRepository.findByIdIn(serviceOptionsId);

		return new ReqServiceOptionsDto(pickedOptions, serviceOptions);
	}


}
