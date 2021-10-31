package com.subscribe.platform.services.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.common.model.PageableDto;
import com.subscribe.platform.services.dto.ReqRegistServiceDto;
import com.subscribe.platform.services.dto.ResStoreServiceDto;
import com.subscribe.platform.services.dto.ReqModifyServiceDto;
import com.subscribe.platform.services.service.StoreServicesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreServicesController {

    private final StoreServicesService storeServicesService;

    @PostMapping(value = "/store/service", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "판매자 서비스 등록")
    public void createService(@Validated @ModelAttribute("createServiceDto") ReqRegistServiceDto createServiceDto) throws Exception {
        storeServicesService.createService(createServiceDto);
    }


    @GetMapping("/store/services")
    @ApiOperation(value = "판매자 서비스 리스트 조회")
    public ListResponse getStoreServiceList(PageableDto pageableDto) throws Exception {
        return storeServicesService.getStoreServiceList(pageableDto.toPageRequest());
    }


    @GetMapping("/store/services/{id}")
    @ApiOperation(value = "판매자 서비스 상세정보 조회")
    public ResStoreServiceDto getStoreServiceDetail(@PathVariable Long id) throws Exception {
        return storeServicesService.getStoreServiceDetail(id);
    }


    @PatchMapping("/store/service")
    @ApiOperation(value = "판매자 서비스 수정")
    public void updateService(@Validated @ModelAttribute ReqModifyServiceDto updateServiceDto) throws Exception {

        // 삭제된 옵션, 이미지, 카테고리 처리
        storeServicesService.deleteServicesAdditionalResource(updateServiceDto);
        // 서비스 수정
        storeServicesService.updateService(updateServiceDto);
    }


    @DeleteMapping("/store/service/{id}")
    @ApiOperation(value = "판매자 서비스 삭제")
    public void deleteService(@PathVariable(value = "id") Long id) throws Exception {
        storeServicesService.deleteService(id);
    }


}
