package com.subscribe.platform.subscribe.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.subscribe.dto.ResSubscribeListDto;
import com.subscribe.platform.subscribe.service.SubscribeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @GetMapping("/customer/subscribes/{customerId}")
    @ApiOperation(value = "나의 구독(정보) 리스트 조회")
    public ListResponse<ResSubscribeListDto> subscribesByCustomer(@PathVariable("customerId") Long customerId){
        return subscribeService.subscribesByCustomer(customerId);
    }
}
