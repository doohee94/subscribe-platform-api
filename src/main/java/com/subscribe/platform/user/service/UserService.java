package com.subscribe.platform.user.service;

import com.subscribe.platform.user.dto.UpdateStoreDto;
import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.entity.Authority;
import com.subscribe.platform.user.entity.Store;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.AuthorityRepository;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        User user = User.createMemberBuilder()
                .email(createUserDto.getEmail())
                .name(createUserDto.getName())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .authorities(authority)
                .build();

        // 판매자인 경우
        if(createUserDto.isStore()){
            // 판매자정보 생성
            Store store = Store.builder()
                    .storeName(createUserDto.getStoreName())
                    .businessNum(createUserDto.getBusinessNum())
                    .build();

            user.setStore(store);
        }

        userRepository.save(user);

    }

    public Map findCheckByEmail(String email){

        String checkByEmail = userRepository.findCheckByEmail(email);

        Map result = new HashMap();
        result.put("result", checkByEmail);
        return result;
    }

    public void updateStore(String email, UpdateStoreDto request) {
        User user = findByEmail(email);
        Store store = user.getStore();
        store.updateStore(request.getStoreName(), request.getBusinessNum());
        //user.setStore(store);
        userRepository.save(user);
    }

    public List<String> getAuthoritiesByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user.getAuthorities()
                .stream()
                .map(o -> o.getAuthority())
                .collect(Collectors.toList());
    }
}
