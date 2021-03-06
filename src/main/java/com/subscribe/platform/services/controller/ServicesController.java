package com.subscribe.platform.services.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.service.ServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class ServicesController {
    private final ServicesService servicesService;

    /**
     * 판매자 서비스 등록
     */
    @PostMapping("/store/addService")
    public void createService(@ModelAttribute CreateServiceDto createServiceDto) throws Exception {
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

    /**
     * 사용자) 서비스 리스트 조회
     */
    @GetMapping("/getSearchServiceList")
    public ListResponse getSearchServiceList(@RequestParam(value = "serviceName", defaultValue = "") String serviceName, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception{
        return servicesService.getSearchServiceList(serviceName, pageNum, size);
    }

    /**
     * 카테고리 전체 조회
     */
    @GetMapping("/getCategories")
    public ListResponse getCategories(){
        return servicesService.getCategories();
    }

    /**
     * 사용자) 카테고리별 서비스 리스트 조회
     */
    @GetMapping("/getServiceListByCategory/{categoryId}")
    public ListResponse getServiceListByCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception{
        log.info("categoryId = {}", categoryId);
        return servicesService.getServiceListByCategory(categoryId, pageNum, size);
    }

    /**
     * 사용자) 신상서비스 조회 : 최근 3일내에 등록된 서비스 조회
     */
    @GetMapping("/getNewSErviceList")
    public ListResponse getNewServiceList(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "size", defaultValue = "10") int size)throws Exception{
        return servicesService.getNewServiceList(pageNum, size);
    }
}
