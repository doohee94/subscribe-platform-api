package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public void test(String email) {

        System.out.println(userService.findByEmail(email));
    }

    @PostMapping("/sign-up")
    public void createUser(@RequestBody UserDto.CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
    }

    @GetMapping("/checkEmailDupl")
    public Map checkEmailDupl(String email) {
        return userService.findCheckByEmail(email);
    }

    @GetMapping("/getUserInfo")
    public UserDto.getUserDto getUser(String email) {
        User result = userService.findByEmail(email);
        return new UserDto.getUserDto(result.getName(), result.getEmail(), result.getPassword().getPassword());
    }


    @GetMapping("/{id}")
    public String getUserEmail(@PathVariable long id) {

        System.out.println("test");

        return "";
    }
}
