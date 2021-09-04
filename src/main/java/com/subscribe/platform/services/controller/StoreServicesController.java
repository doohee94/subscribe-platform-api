package com.subscribe.platform.services.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.dto.CreateServiceDto;
import com.subscribe.platform.services.dto.ResStoreServiceDto;
import com.subscribe.platform.services.dto.UpdateServiceDto;
import com.subscribe.platform.services.service.ServicesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreServicesController {

    private final ServicesService servicesService;

    @PostMapping(value = "/store/service", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "판매자 서비스 등록")
    public void createService(@ModelAttribute CreateServiceDto createServiceDto) throws Exception {
        servicesService.createService(createServiceDto);
    }


    @GetMapping("/store/services")
    @ApiOperation(value = "판매자 서비스 리스트 조회")
    public ListResponse getStoreServiceList(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        return servicesService.getStoreServiceList(pageNum, size);
    }


    @GetMapping("/store/services/{id}")
    @ApiOperation(value = "판매자 서비스 상세정보 조회")
    public ResStoreServiceDto getStoreServiceDetail(@PathVariable Long id) throws Exception {
        return servicesService.getStoreServiceDetail(id);
    }


    @PatchMapping("/store/service")
    @ApiOperation(value = "판매자 서비스 수정")
    public void updateService(@ModelAttribute UpdateServiceDto updateServiceDto) throws Exception {

        // 삭제된 옵션, 이미지, 카테고리 처리
        servicesService.deleteServicesAdditionalResource(updateServiceDto);
        // 서비스 수정
        servicesService.updateService(updateServiceDto);
    }


    @DeleteMapping("/store/service/{id}")
    @ApiOperation(value = "판매자 서비스 삭제")
    public void deleteService(@RequestParam Long id) throws Exception {
        servicesService.deleteService(id);
    }


}
