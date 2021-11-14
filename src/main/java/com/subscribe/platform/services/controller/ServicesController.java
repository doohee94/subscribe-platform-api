package com.subscribe.platform.services.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.common.model.PageableDto;
import com.subscribe.platform.common.model.SelectBox;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.service.ServicesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServicesController {

    private final ServicesService servicesService;

    @GetMapping("/services")
    @ApiOperation(value = "사용자 서비스 리스트 조회")
    public ListResponse<ResServiceListDto> getSearchServiceList(@RequestParam(value = "serviceName", defaultValue = "") String serviceName, PageableDto pageableDto) {
        return new ListResponse<>(servicesService.getSearchServiceList(serviceName, pageableDto.toPageRequest()));
    }

    @GetMapping("/categories")
    @ApiOperation(value = "카테고리 전체 조회")
    public List<SelectBox> getCategories() {
        return servicesService.getCategories();
    }


    @GetMapping("/categories/{categoryId}/services")
    @ApiOperation(value = "사용자 카테고리별 서비스 리스트 조회")
    public ListResponse getServiceListByCategory(@PathVariable(value = "categoryId") Long categoryId, PageableDto pageableDto) throws Exception {

//        new PageableDto(pageableDto.getPage(), pageableDto.getSize(), pageableDto.getSort());

        return servicesService.getServiceListByCategory(categoryId, pageableDto.toPageRequest());
    }

    @GetMapping("/services/new")
    @ApiOperation(value = "사용자 신상서비스 조회 : 최근 3일내에 등록된 서비스 조회")
    public ListResponse getNewServiceList(PageableDto pageableDto) throws Exception {
        return servicesService.getNewServiceList(pageableDto.toPageRequest());
    }


    @GetMapping("/services/{id}")
    @ApiOperation(value = "서비스 상세 조회")
    public ResServiceDetailDto getServiceById(@PathVariable Long id){
        return servicesService.getServiceById(id);
    }


    @PostMapping(value="/customer/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "후기 등록")
    public Object writeReview(@Validated @ModelAttribute("requestReviewDto") ReqRegistReviewDto reviewDto) throws Exception{

        servicesService.writeReview(reviewDto);

        return reviewDto;
    }
}
