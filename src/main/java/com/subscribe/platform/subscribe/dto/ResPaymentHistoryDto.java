package com.subscribe.platform.subscribe.dto;

import com.subscribe.platform.services.entity.Services;
import com.subscribe.platform.subscribe.entity.PaymentResult;
import com.subscribe.platform.subscribe.entity.Subscribe;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class ResPaymentHistoryDto {
    private List<String> serviceNames;
    private String creditCardCompany;
    private String paidCardNo;
    private int payPrice;
    private String payDate;

    public ResPaymentHistoryDto(List<Services> services, PaymentResult paymentResult){
        this.serviceNames = services.stream().map(o -> o.getName()).collect(Collectors.toList());
        this.creditCardCompany = paymentResult.getCreditCardCompany();
        this.paidCardNo = paymentResult.getPaidCardNo();
        this.payPrice = paymentResult.getPayPrice();
        this.payDate = paymentResult.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
