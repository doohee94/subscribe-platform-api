package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public void test(String email){

        System.out.println(userService.findByEmail(email));
    }

    @PostMapping("/sign-up")
    public void createUser(@RequestBody UserDto.CreateUserDto createUserDto){
        userService.createUser(createUserDto);
    }

    @GetMapping("/{id}")
    public String getUserEmail(@PathVariable long id){

        System.out.println("test");

        return "";
    }

}
