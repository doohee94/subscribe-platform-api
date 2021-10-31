package com.subscribe.platform.subscribe.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.subscribe.dto.*;
import com.subscribe.platform.subscribe.service.SubscribeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @GetMapping("/subscribes")
    @ApiOperation(value = "나의 구독(정보) 리스트 조회")
    public ListResponse<ResSubscribeListDto> subscribesByCustomer() throws Exception{
        return subscribeService.subscribesByCustomer();
    }

    @PatchMapping("/subscribe/cancel")
    @ApiOperation(value = "구독취소")
    public void cancelSubscribe(@RequestBody ReqCancelSubscribeDto cancelSubscribeDto) throws Exception{
        subscribeService.cancelSubscribe(cancelSubscribeDto);
    }

    @GetMapping("/shoppinglist")
    @ApiOperation(value = "장바구니 리스트 조회")
    public ListResponse<ResShoppingDto> shoppingList() throws Exception{
        return subscribeService.shoppingList();
    }

    @PostMapping("/subscribe")
    @ApiOperation(value = "구독하기")
    public void subscribe(@ModelAttribute("reqPayInfoDto") ReqPayInfoDto reqPayInfoDto) throws Exception{
        subscribeService.subscribe(reqPayInfoDto);
    }

    @PostMapping("/shopping")
    @ApiOperation(value = "장바구니 넣기")
    public void addShoppingList(@RequestBody CreateShoppingDto shoppingDto){
        subscribeService.addShoppingList(shoppingDto);
    }
}
