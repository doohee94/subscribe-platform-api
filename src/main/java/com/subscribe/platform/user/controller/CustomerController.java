package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.CustomerUpdateDto;
import com.subscribe.platform.user.dto.ResCustomerDto;
import com.subscribe.platform.user.service.CustomerService;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping
    public void UpdateCustomers(@RequestBody CustomerUpdateDto customerUpdateDto){

        customerService.updateCustomers(customerUpdateDto);
    }

    @GetMapping
    public ResCustomerDto findCustomer(){
        return customerService.findCustomer();
    }

}
