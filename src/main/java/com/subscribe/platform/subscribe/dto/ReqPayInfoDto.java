package com.subscribe.platform.subscribe.dto;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.List;

@Data
public class ReqPayInfoDto {
    List<Long> subscribeIds;

    @CreditCardNumber
    private String cardNo;
    private String creditCardCompany;
}
