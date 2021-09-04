package com.subscribe.platform.user.dto;

import com.subscribe.platform.user.entity.Address;
import com.subscribe.platform.user.entity.Password;
import com.subscribe.platform.user.entity.Phone;
import com.subscribe.platform.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class ResCustomerDto {
    private String name;
    private String email;
    private Phone phone;
    private Address address;


    public ResCustomerDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getCustomer().getPhone();
        this.address = user.getCustomer().getAddress();
    }
}
