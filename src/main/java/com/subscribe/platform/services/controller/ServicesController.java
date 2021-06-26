package com.subscribe.platform.services.controller;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class ServicesController {
    private final ServicesService servicesService;

    /**
     * 판매자 서비스 등록
     */
    @PostMapping("/store/addService")
    public void createService(@ModelAttribute CreateServiceDto createServiceDto){   // @ModelAttribute - form으로 전송받을 때 사용

//        MultipartFile imageFile = createServiceDto.getServiceImages().get(0).getImageFile();
//        MultipartFile imageFile = createServiceDto.getImageFile();
//
//        FileHandler fileHandler = new FileHandler();
//        FileInfo fileInfo = fileHandler.getFileInfo(imageFile);
//        System.out.println("fileInfo = " + fileInfo);



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

    /**
     * 판매자 서비스 리스트 조회
     */
    @GetMapping("/store/getServiceList")
    public ListResponse getStoreServiceList(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "size", defaultValue = "10") int size){
        return servicesService.getStoreServiceList(pageNum, size);
//        serviceList.forEach(m -> System.out.println(m));
    }

    @GetMapping("/store/getServiceDetail")
    public void getStoreServiceDetail(@RequestParam Long serviceId){
        servicesService.getStoreServiceDetail(serviceId);
    }
}
