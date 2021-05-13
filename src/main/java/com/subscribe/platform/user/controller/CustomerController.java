package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.CustomerUpdateDto;
import com.subscribe.platform.user.service.CustomerService;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final UserService userService;
    private final CustomerService customerService;

    @PutMapping
    public void UpdateCustomers(@RequestBody CustomerUpdateDto customerUpdateDto){

        customerService.upDateCustomers(customerUpdateDto);



    }

}
