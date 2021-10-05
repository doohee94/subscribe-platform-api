package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.subscribe.dto.ReqPaymentHistoryDto;
import com.subscribe.platform.subscribe.dto.ResPaymentHistoryDto;
import com.subscribe.platform.subscribe.repository.PaymentResultRepository;
import com.subscribe.platform.subscribe.repository.SubscribeRepository;
import com.subscribe.platform.user.entity.Customer;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final PaymentResultRepository paymentResultRepository;

    public void paymentResult(ReqPaymentHistoryDto reqPaymentHistoryDto, PageRequest pageRequest){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = userRepository.findByEmail(email).getCustomer();
    }
}
