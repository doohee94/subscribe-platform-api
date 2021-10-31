package com.subscribe.platform.user.dto;

import com.subscribe.platform.user.entity.Address;
import com.subscribe.platform.user.entity.Customer;
import com.subscribe.platform.user.entity.Phone;
import lombok.Getter;

@Getter
public class ReqModifyCustomerDto {

    private Address address;
    private Phone phone;

    public Customer toEntity() {
        return Customer.builder()
                .address(this.address)
                .phone(this.phone)
                .build();

    }
}
