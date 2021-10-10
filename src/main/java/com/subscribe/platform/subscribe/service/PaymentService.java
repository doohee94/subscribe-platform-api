package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.subscribe.dto.ReqPaymentHistoryDto;
import com.subscribe.platform.subscribe.dto.ResPaymentHistoryDto;
import com.subscribe.platform.subscribe.entity.PaymentResult;
import com.subscribe.platform.subscribe.entity.Subscribe;
import com.subscribe.platform.subscribe.repository.PaymentResultRepository;
import com.subscribe.platform.subscribe.repository.SubscribeRepository;
import com.subscribe.platform.user.entity.Customer;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final PaymentResultRepository paymentResultRepository;

    public ListResponse<ResPaymentHistoryDto> paymentResult(ReqPaymentHistoryDto reqPaymentHistoryDto, PageRequest pageRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = userRepository.findByEmail(email).getCustomer();

        Page<PaymentResult> paymentResults = paymentResultRepository.findByOwnerIdAndCreatedDateBetween(
                customer.getId(),
                LocalDateTime.of(LocalDate.parse(String.valueOf(reqPaymentHistoryDto.getStartDate()), DateTimeFormatter.ofPattern("yyyyMMdd")), LocalTime.of(0, 0)),
                LocalDateTime.of(LocalDate.parse(String.valueOf(reqPaymentHistoryDto.getEndDate()), DateTimeFormatter.ofPattern("yyyyMMdd")).plusDays(1), LocalTime.of(0, 0)),
                pageRequest
        );

        List<ResPaymentHistoryDto> result = paymentResults.stream().map(
                paymentResult -> new ResPaymentHistoryDto(
                        subscribeRepository.findByIdIn(
                                Arrays.stream(paymentResult.getSubscribes().split(","))
                                        .map(o -> Long.parseLong(o))
                                        .collect(Collectors.toList())
                        ).stream().map(o -> o.getServices()).collect(Collectors.toList()),
                        paymentResult
                )
        ).collect(Collectors.toList());

        return new ListResponse(result);
    }
}
