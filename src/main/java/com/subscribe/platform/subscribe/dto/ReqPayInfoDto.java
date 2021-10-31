package com.subscribe.platform.subscribe.dto;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ReqPayInfoDto {

    @Size(min = 1)
    List<Long> subscribeIds;

    @CreditCardNumber
    private String cardNo;
    private String creditCardCompany;
}
