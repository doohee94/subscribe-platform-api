package com.subscribe.platform.services.service;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ServicesServiceTest {

    @Autowired
    private ServicesService servicesService;

    @Test
    public void createServiceTest(){

//        List<CreateServiceOptionDto> optionDtoList = new ArrayList<>();
//        for(int i=0; i<3; i++){
//            CreateServiceOptionDto optionDto = new CreateServiceOptionDto();
//            optionDto.setOptionName("option"+i);
//            optionDto.setPrice(1000+i);
//            optionDto.setStock(i+1);
//            optionDto.setMaxCount(i);
//            optionDtoList.add(optionDto);
//        }
//
//        List<CreateServiceImageDto> imageDtoList = new ArrayList<>();
//        for(int i=0; i<4; i++){
//            CreateServiceImageDto imageDto = new CreateServiceImageDto();
//            if(i == 2){
//                imageDto.setImageType("THUMBNAIL");
//            }else{
//                imageDto.setImageType("DETAIL");
//            }
//            imageDto.setImageSeq(i);
//
//            imageDtoList.add(imageDto);
//        }
//
//        List<CreateCategoryDto> categoryDtoList = new ArrayList<>();
//
//        CreateServiceDto createServiceDto = new CreateServiceDto();
//        createServiceDto.setServiceName("테스트 서비스");
//        createServiceDto.setServiceCycle("MONTH");
//        createServiceDto.setAvailableDay("1,11,31");
//        createServiceDto.setServiceOptions(optionDtoList);
//        createServiceDto.setServiceImages(imageDtoList);
//        createServiceDto.setCategories(categoryDtoList);
//
//        servicesService.createService(createServiceDto);
    }

    @Test
    public void getServiceListTest(){
        //ListResponse serviceList = servicesService.getServiceList(0, 10);
    }
}