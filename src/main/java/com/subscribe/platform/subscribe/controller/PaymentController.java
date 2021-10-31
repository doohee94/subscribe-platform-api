package com.subscribe.platform.subscribe.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.common.model.PageableDto;
import com.subscribe.platform.subscribe.dto.ReqPaymentHistoryDto;
import com.subscribe.platform.subscribe.dto.ResPaymentHistoryDto;
import com.subscribe.platform.subscribe.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment-results")
    @ApiOperation(value = "결제내역 조회")
    public ListResponse<ResPaymentHistoryDto> paymentResults(@Valid @ModelAttribute ReqPaymentHistoryDto reqPaymentHistoryDto, PageableDto pageableDto){
        return paymentService.paymentResult(reqPaymentHistoryDto, pageableDto.toPageRequest());
    }
}
