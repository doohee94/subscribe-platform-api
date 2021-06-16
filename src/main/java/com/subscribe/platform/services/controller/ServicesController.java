package com.subscribe.platform.services.controller;

import com.subscribe.platform.services.dto.CategoryDto;
import com.subscribe.platform.services.dto.CreateServiceDto;
import com.subscribe.platform.services.dto.ServiceImageDto;
import com.subscribe.platform.services.dto.ServiceOptionDto;
import com.subscribe.platform.services.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class ServicesController {
    private final ServicesService servicesService;

    @PostMapping("/addService")
    public void createService(CreateServiceDto createServiceDto, MultipartHttpServletRequest multipartHttpServletRequest){

//        List<ServiceOptionDto> optionDtoList = new ArrayList<>();
//        for(int i=0; i<3; i++){
//            ServiceOptionDto optionDto = new ServiceOptionDto();
//            optionDto.setOptionName("option"+i);
//            optionDto.setPrice(1000+i);
//            optionDto.setStock(i+1);
//            optionDto.setMaxCount(i);
//            optionDtoList.add(optionDto);
//        }
//
//        List<ServiceImageDto> imageDtoList = new ArrayList<>();
//        for(int i=0; i<4; i++){
//            ServiceImageDto imageDto = new ServiceImageDto();
//            if(i == 2){
//                imageDto.setImageType("THUMBNAIL");
//            }else{
//                imageDto.setImageType("DETAIL");
//            }
//            imageDto.setImageSeq(i);
//            imageDto.setImageName("image"+i);
//            imageDto.setFakeName("fakeImage"+i);
//
//            imageDtoList.add(imageDto);
//        }
//
//        List<CategoryDto> categoryDtoList = new ArrayList<>();
//        for(int i=0; i<2; i++){
//            CategoryDto categoryDto = new CategoryDto();
//            categoryDto.setCategoryId(i+1L);
//            categoryDtoList.add(categoryDto);
//        }
//
//        CreateServiceDto createServiceDto = new CreateServiceDto();
//        createServiceDto.setServiceName("테스트 서비스");
//        createServiceDto.setServiceCycle("MONTH");
//        createServiceDto.setAvailableDay("1,11,31");
//        createServiceDto.setServiceOptions(optionDtoList);
//        createServiceDto.setServiceImages(imageDtoList);
//        createServiceDto.setServiceCategories(categoryDtoList);


        servicesService.createService(createServiceDto);
    }
}
