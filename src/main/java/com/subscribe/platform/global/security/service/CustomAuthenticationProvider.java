package com.subscribe.platform.global.security.service;

import com.subscribe.platform.global.security.InvalidLoginInputException;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findByEmail(email);

        if (!user.getPassword().isPasswordMatches(password)){
            throw new InvalidLoginInputException("password is not valid");
        }

        return new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
