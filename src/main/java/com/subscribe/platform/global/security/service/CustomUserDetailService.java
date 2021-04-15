package com.subscribe.platform.global.security.service;

import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail().getEmail(),
                user.getPassword().getPassword(),
                user.getAuthorities());
    }
}
