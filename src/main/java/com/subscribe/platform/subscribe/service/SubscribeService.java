package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.entity.ServiceOption;
import com.subscribe.platform.services.repository.ServiceOptionRepository;
import com.subscribe.platform.subscribe.dto.ResSubscribeListDto;
import com.subscribe.platform.subscribe.entity.Subscribe;
import com.subscribe.platform.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final ServiceOptionRepository serviceOptionRepository;

    public ListResponse<ResSubscribeListDto> subscribesByCustomer(Long customerId){
        List<Subscribe> subscribes = subscribeRepository.findByCustomerId(customerId);


//        List<ResSubscribeListDto> result = subscribes.stream().map(
//                subscribe -> ResSubscribeListDto.builder()
//                        .serviceName("일단테스트")
//                        .subscribe(subscribe)
//                        .serviceOptions(
//                                serviceOptionRepository.findByIdIn(subscribe.getSelectedOptions().split(","))
//                        )
//                        .paidCount(0)
//                        .build()
//        ).collect(Collectors.toList());
//
//
//        return new ListResponse(result);

        for (Subscribe subscribe : subscribes) {

//            String[] split = subscribe.getSelectedOptions().split(",");
//            Arrays.stream(split).

//            List<ServiceOption> option = serviceOptionRepository.findByIdIn();
//            log.info("option name = {}", option.get(0).getName());
        }

        return new ListResponse<>(null);
    }
}
