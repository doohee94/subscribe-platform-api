package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/test")
    public void test(@RequestBody UserDto.CreateUserDto user){
        System.out.println(user);
    }


}
