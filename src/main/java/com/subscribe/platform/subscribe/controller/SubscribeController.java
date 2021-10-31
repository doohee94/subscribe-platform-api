package com.subscribe.platform.subscribe.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.subscribe.dto.ReqCancelSubscribeDto;
import com.subscribe.platform.subscribe.dto.ReqPayInfoDto;
import com.subscribe.platform.subscribe.dto.ResShoppingDto;
import com.subscribe.platform.subscribe.dto.ResSubscribeListDto;
import com.subscribe.platform.subscribe.service.SubscribeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @GetMapping("/subscribes")
    @ApiOperation(value = "나의 구독(정보) 리스트 조회")
    public ListResponse<ResSubscribeListDto> subscribesByCustomer(){
        return subscribeService.subscribesByCustomer();
    }

    @PatchMapping("/subscribe/cancel")
    @ApiOperation(value = "구독취소")
    public void cancelSubscribe(@Validated @RequestBody ReqCancelSubscribeDto cancelSubscribeDto){

        subscribeService.cancelSubscribe(cancelSubscribeDto);
    }

    @GetMapping("/shoppinglist")
    @ApiOperation(value = "장바구니 리스트 조회")
    public ListResponse<ResShoppingDto> shoppingList(){
        return subscribeService.shoppingList();
    }

    @DeleteMapping("/remove-shopping")
    @ApiOperation(value = "장바구니 목록 삭제")
    public void removeShopping(@RequestParam("subscribeId") Long subscribeId){
        subscribeService.removeShopping(subscribeId);
    }

    @PostMapping("/subscribe")
    @ApiOperation(value = "구독하기")
    public void subscribe(@Validated @RequestBody ReqPayInfoDto reqPayInfoDto){

        subscribeService.subscribe(reqPayInfoDto);
    }
}
