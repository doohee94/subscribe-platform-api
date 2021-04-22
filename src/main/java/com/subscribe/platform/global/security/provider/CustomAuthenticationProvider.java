package com.subscribe.platform.global.security.provider;

import com.subscribe.platform.global.error.exception.InvalidValueException;
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

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findByEmail(email);

        if(!user.isMatchPassword(password)){
            throw new InvalidValueException("password is not valid");
        }


        return new UsernamePasswordAuthenticationToken(email, password,user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
