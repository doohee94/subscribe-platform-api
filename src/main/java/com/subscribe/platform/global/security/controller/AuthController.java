package com.subscribe.platform.global.security.controller;

import com.subscribe.platform.global.security.model.LoginRequest;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public void signUp(@RequestBody LoginRequest loginRequest) {

        System.out.println(loginRequest);

    }



}
