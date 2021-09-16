package com.subscribe.platform.subscribe.dto;

import com.subscribe.platform.services.entity.ServiceOption;
import com.subscribe.platform.subscribe.entity.Subscribe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResSubscribeListDto {
    private String serviceName;
    private String subscribeStartDate;
    private int subscribePeriod;
    private List<ResSubsOptionDto> options;
    private int paidCount;
    private String nextPayDate;

    @Builder
    public ResSubscribeListDto(String serviceName, Subscribe subscribe, List<ServiceOption> serviceOptions, int paidCount) {
        this.serviceName = serviceName;
        this.subscribeStartDate = subscribe.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.subscribePeriod = Period.between(subscribe.getCreatedDate().toLocalDate(), LocalDate.now()).getDays();
        this.options = serviceOptions.stream().map(
                option -> new ResSubsOptionDto(option.getName(), option.getPrice())
        ).collect(Collectors.toList());
        this.paidCount = paidCount;
        this.nextPayDate = subscribe.getPayScheduledDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
