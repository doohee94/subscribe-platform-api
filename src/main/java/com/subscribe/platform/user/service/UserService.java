package com.subscribe.platform.user.service;

import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
