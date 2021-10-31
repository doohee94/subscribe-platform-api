package com.subscribe.platform.user.service;

import com.subscribe.platform.user.dto.ReqModifyCustomerDto;
import com.subscribe.platform.user.dto.ResCustomerDto;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final UserService userService;

    private final UserRepository userRepository;

    @Transactional
    public void updateCustomers(ReqModifyCustomerDto reqModifyCustomerDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        user.updateCustomer(reqModifyCustomerDto);

        userRepository.save(user);

    }

    public ResCustomerDto findCustomer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        return new ResCustomerDto(user);

    }

}
