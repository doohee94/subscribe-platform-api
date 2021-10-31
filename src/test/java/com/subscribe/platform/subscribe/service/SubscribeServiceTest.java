package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.services.repository.ServiceOptionRepository;
import com.subscribe.platform.subscribe.dto.ReqPayInfoDto;
import com.subscribe.platform.subscribe.repository.PaymentResultRepository;
import com.subscribe.platform.subscribe.repository.SubscribeRepository;
import com.subscribe.platform.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscribeServiceTest {

    @Autowired
    SubscribeRepository subscribeRepository;
    @Autowired
    ServiceOptionRepository serviceOptionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentResultRepository paymentResultRepository;
    SubscribeService subscribeService = new SubscribeService(subscribeRepository, serviceOptionRepository, userRepository, paymentResultRepository);

    @Test
     void subscribeTest(){
        ReqPayInfoDto reqPayInfoDto = new ReqPayInfoDto();
        List<Long> ids = new ArrayList<>();
        ids.add(3L);
        reqPayInfoDto.setSubscribeIds(ids);
        reqPayInfoDto.setCardNo("12345098");
        reqPayInfoDto.setCreditCardCompany("우리카드");

        subscribeService.subscribe(reqPayInfoDto);

    }

    @Test
    void removeShoppingTest(){
        Long id = 3L;
        subscribeService.removeShopping(id);

    }
}