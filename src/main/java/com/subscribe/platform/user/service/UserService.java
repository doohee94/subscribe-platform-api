package com.subscribe.platform.user.service;

import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.entity.Authority;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.AuthorityRepository;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void createUser(UserDto.CreateUserDto createUserDto) {

        String authName = createUserDto.isStore() ? "STORE" : "MEMBER";
        Authority authority = authorityRepository.findByAuthority(authName);

        User user = User.builder()
                .email(createUserDto.getEmail())
                .name(createUserDto.getName())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .authorities(authority)
                .build();

        userRepository.save(user);

    }
}
