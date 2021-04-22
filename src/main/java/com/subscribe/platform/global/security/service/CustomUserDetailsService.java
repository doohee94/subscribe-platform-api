package com.subscribe.platform.global.security.service;

import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 유저 정보를 반환하는 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * 유저 정보를 조회 후 반환.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.subscribe.platform.user.entity.User user = userService.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. :::"+username);
        }

        return new User(user.getEmail().getEmail(), user.getPassword().getPassword(),user.getAuthorities());
    }
}