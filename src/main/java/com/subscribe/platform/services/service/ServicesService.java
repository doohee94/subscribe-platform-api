package com.subscribe.platform.services.service;

import com.subscribe.platform.services.dto.CategoryDto;
import com.subscribe.platform.services.dto.CreateServiceDto;
import com.subscribe.platform.services.dto.ServiceImageDto;
import com.subscribe.platform.services.dto.ServiceOptionDto;
import com.subscribe.platform.services.entity.*;
import com.subscribe.platform.services.repository.CategoryRepository;
import com.subscribe.platform.services.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicesService {
    private final ServicesRepository servicesRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 서비스 등록
     */
    public void createService(CreateServiceDto dto){

        // 서비스 옵션 담기
        List<ServiceOption> serviceOptionList = new ArrayList<>();
        for(ServiceOptionDto serviceOptionDto : dto.getServiceOptions()){
            ServiceOption option = ServiceOption.builder()
                    .name(serviceOptionDto.getOptionName())
                    .price(serviceOptionDto.getPrice())
                    .stock(serviceOptionDto.getStock())
                    .maxCount(serviceOptionDto.getMaxCount())
                    .build();
            serviceOptionList.add(option);
        }

        // 서비스 이미지 담기
        List<ServiceImage> serviceImageList = new ArrayList<>();
        for(ServiceImageDto serviceImageDto : dto.getServiceImages()){

            ServiceImage image = ServiceImage.builder()
                    .name(serviceImageDto.getImageName())
                    .fakeName(serviceImageDto.getFakeName())
                    .imageType("THUMBNAIL".equals(serviceImageDto.getImageType()) ? ImageType.THUMBNAIL : ImageType.DETAIL)
                    .imageSeq(serviceImageDto.getImageSeq())
                    .build();
            serviceImageList.add(image);
        }

        // 서비스 카테고리 담기
        List<Category> categoryList = new ArrayList<>();
        for(CategoryDto categoryDto : dto.getServiceCategories()){
            Optional<Category> category = categoryRepository.findById(categoryDto.getCategoryId());

            categoryList.add(category.get());

//            ServiceCategory sc = ServiceCategory.createServiceCategory(category);
//            serviceCategoryList.add(sc);
        }

        // 서비스 생성
        Services services = Services.builder()
                .name(dto.getServiceName())
                .serviceCycle("MONTH".equals(dto.getServiceCycle()) ? ServiceCycle.MONTH : ServiceCycle.WEEK)
                .availableDay(dto.getAvailableDay())
                .serviceOptions(serviceOptionList)
                .serviceImages(serviceImageList)
                .categories(categoryList)
                .build();

        // 서비스 저장
        servicesRepository.save(services);
    }
}
