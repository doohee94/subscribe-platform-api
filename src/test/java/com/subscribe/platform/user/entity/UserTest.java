package com.subscribe.platform.user.entity;

import com.subscribe.platform.user.repository.AuthorityRepository;
import com.subscribe.platform.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createMemberTest(){

        Authority authority = authorityRepository.findByAuthority("MEMBER");

        final User user = User.createMemberBuilder()
                .email("maro42@naver.com")
                .name("")
                .password("1234")
                .authorities(authority)
                .build();

        userRepository.save(user);
    }
}