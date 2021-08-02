package com.subscribe.platform.services.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class ServicesController {
    private final ServicesService servicesService;

    /**
     * 판매자 서비스 등록
     */
    @PostMapping("/store/addService")
    public void createService(@ModelAttribute CreateServiceDto createServiceDto) throws Exception {   // @ModelAttribute - form으로 전송받을 때 사용
        servicesService.createService(createServiceDto);
    }

    /**
     * 판매자 서비스 리스트 조회
     */
    @GetMapping("/store/getServiceList")
    public ListResponse getStoreServiceList(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception{
        return servicesService.getStoreServiceList(pageNum, size);
    }

    /**
     * 판매자 서비스 상세정보 조회
     */
    @GetMapping("/store/getServiceDetail")
    public ResStoreServiceDto getStoreServiceDetail(@RequestParam Long serviceId) throws Exception{
        return servicesService.getStoreServiceDetail(serviceId);
    }

    /**
     * 판매자 서비스 수정
     */
    @PatchMapping("/store/modifyService")
    public void updateService(@ModelAttribute UpdateServiceDto updateServiceDto) throws Exception{

        // 삭제된 옵션, 이미지, 카테고리 처리
        servicesService.deleteServicesAdditionalResource(updateServiceDto);
        // 서비스 수정
        servicesService.updateService(updateServiceDto);
    }

    /**
     * 판매자 서비스 삭제
     */
    @DeleteMapping("/store/deleteService")
    public void deleteService(@RequestParam Long serviceId) throws Exception{
        servicesService.deleteService(serviceId);
    }


}
