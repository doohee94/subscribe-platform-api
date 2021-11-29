package com.subscribe.platform.services.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.entity.ServiceOption;
import com.subscribe.platform.subscribe.entity.PickedOption;
import com.subscribe.platform.user.entity.Address;

import lombok.Data;

@Data
public class ReqServiceOptionsDto {

	private Address address;
	private ListResponse<ServiceOptionDto> options;

	public ReqServiceOptionsDto(List<PickedOption> pickedOptions, List<ServiceOption> serviceOptions) {
		this.address = pickedOptions.get(0).getSubscribe().getCustomer().getAddress();

		List<ServiceOptionDto> options = pickedOptions.stream()
			.map(t -> new ServiceOptionDto(t, serviceOptions))
			.collect(Collectors.toList());
		this.options = new ListResponse<>(options);
	}

	@Data
	public static class ServiceOptionDto{

		private String optionName;
		private int quantity;

		public ServiceOptionDto(PickedOption pickedOption, List<ServiceOption> serviceOptions) {
			this.optionName = serviceOptions.stream()
				.filter(t->t.getId().equals(pickedOption.getOptionId()))
				.findFirst()
				.orElseThrow(EntityNotFoundException::new)
				.getName();
			this.quantity = pickedOption.getQuantity();
		}
	}

}
