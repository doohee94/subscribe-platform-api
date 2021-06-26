package com.subscribe.platform.services.service;

import com.subscribe.platform.common.handler.FileHandler;
import com.subscribe.platform.common.model.FileInfo;
import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.entity.*;
import com.subscribe.platform.services.repository.CategoryRepository;
import com.subscribe.platform.services.repository.ServicesRepository;
import com.subscribe.platform.user.entity.Store;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.StoreRepository;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicesService {
    private final ServicesRepository servicesRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    /**
     * 서비스 등록
     */
    public void createService(CreateServiceDto dto){

        // 서비스 옵션 담기
        List<ServiceOption> serviceOptionList = new ArrayList<>();
        for(CreateServiceOptionDto serviceOptionDto : dto.getServiceOptions()){
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
        for(CreateServiceImageDto serviceImageDto : dto.getServiceImages()){

            FileHandler fileHandler = new FileHandler();
            FileInfo fileInfo = fileHandler.getFileInfo(serviceImageDto.getImageFile());

            ServiceImage image = ServiceImage.builder()
                    .name(fileInfo.getOriginName())
                    .fakeName(fileInfo.getFakeName())
                    .extensionName(fileInfo.getExtensionName())
                    .imageType("THUMBNAIL".equals(serviceImageDto.getImageType()) ? ImageType.THUMBNAIL : ImageType.DETAIL)
                    .imageSeq(serviceImageDto.getImageSeq())
                    .build();
            serviceImageList.add(image);
        }

        // 서비스 카테고리 담기
        List<Category> categoryList = new ArrayList<>();
        for(CreateCategoryDto categoryDto : dto.getCategories()){
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

        // 유저 정보 가져오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);

        Optional<Store> store = storeRepository.findById(user.getStore().getId());

        // 유저정보 저장
        services.setStore(store.get());

        // 서비스 저장
        servicesRepository.save(services);
    }

    /**
     * 판매자) 서비스 리스트 조회
     * @return
     */
    public ListResponse getStoreServiceList(int pageNum, int size){
        
        // 페이징 정보
        PageRequest pageRequest = PageRequest.of(pageNum, size);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Long storeId = user.getStore().getId();

        Page<Services> serviceList = servicesRepository.findByStore_Id(storeId, pageRequest);

        List<ResServiceListDto> result = serviceList.stream()
                .map(m -> new ResServiceListDto(m))
                .collect(Collectors.toList());

        return new ListResponse(result, serviceList.getTotalElements());
    }

    public void getStoreServiceDetail(Long serviceId) {
    }
}
