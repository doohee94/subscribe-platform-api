package com.subscribe.platform.user.service;

import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.exception.UserNotFoundException;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUser(long id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException();
        }

        return new UserDto(user.get());
    }
}
