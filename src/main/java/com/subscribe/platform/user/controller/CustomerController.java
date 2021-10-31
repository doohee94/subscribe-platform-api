package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.ReqModifyCustomerDto;
import com.subscribe.platform.user.dto.ResCustomerDto;
import com.subscribe.platform.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping
    public void UpdateCustomers(@RequestBody ReqModifyCustomerDto reqModifyCustomerDto){

        customerService.updateCustomers(reqModifyCustomerDto);
    }

    @GetMapping
    public ResCustomerDto findCustomer(){
        return customerService.findCustomer();
    }

}
