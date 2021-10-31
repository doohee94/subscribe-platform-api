package com.subscribe.platform.subscribe.dto;

import com.subscribe.platform.services.entity.ServiceCycle;
import com.subscribe.platform.services.entity.Services;
import com.subscribe.platform.subscribe.entity.PickedOption;
import com.subscribe.platform.subscribe.entity.Status;
import com.subscribe.platform.subscribe.entity.Subscribe;
import com.subscribe.platform.user.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateShoppingDto {

    private long serviceId;
    private ServiceCycle deliveryCycle; // 배송주기 : WEEK, MONTH
    private String deliveryDay; // 배송일 : 요일 or 날짜
    private List<PickedOptionDto> options;

    public Subscribe of(Customer customer, Services services) {

        Set<PickedOption> pickedOptions = options.stream()
                .map(PickedOptionDto::of)
                .collect(Collectors.toSet());

        return Subscribe.builder()
                .status(Status.SHOPPING)
                .deliveryCycle(deliveryCycle)
                .deliveryDay(deliveryDay)
                .shoppingDate(LocalDateTime.now())
                .customer(customer)
                .services(services)
                .pickedOptions(pickedOptions)
                .build();
    }
}
