package com.subscribe.platform.user.service;

import com.subscribe.platform.user.dto.CustomerUpdateDto;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final UserService userService;

    private final UserRepository userRepository;

    @Transactional
    public void upDateCustomers(CustomerUpdateDto customerUpdateDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);

        user.updateCustomer(customerUpdateDto);

        userRepository.save(user);

    }
}
