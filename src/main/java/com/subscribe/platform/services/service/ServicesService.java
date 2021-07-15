package com.subscribe.platform.services.service;

import com.subscribe.platform.common.handler.FileHandler;
import com.subscribe.platform.common.model.FileInfo;
import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.common.properties.GlobalProperties;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.entity.*;
import com.subscribe.platform.services.repository.CategoryRepository;
import com.subscribe.platform.services.repository.ServiceCategoryRepository;
import com.subscribe.platform.services.repository.ServicesQuerydslRepository;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicesService {

    private final FileHandler fileHandler;
    private final GlobalProperties globalProperties;

    private final ServicesRepository servicesRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    /**
     * 서비스 등록
     */
    @Transactional
    public void createService(CreateServiceDto dto) throws IOException {

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

        // 서비스 카테고리 담기
        List<Category> categoryList = new ArrayList<>();
        for(CreateCategoryDto categoryDto : dto.getCategories()){
            Optional<Category> category = categoryRepository.findById(categoryDto.getCategoryId());

            Optional.of(category).ifPresent((value) -> {
                categoryList.add(value.orElseThrow(EntityNotFoundException::new));
            });
//            ServiceCategory sc = ServiceCategory.createServiceCategory(category);
//            serviceCategoryList.add(sc);
        }

        // 서비스 이미지 담기
        List<ServiceImage> serviceImageList = new ArrayList<>();
        for(CreateServiceImageDto serviceImageDto : dto.getServiceImages()){

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

        // 서비스 생성
        Services services = Services.builder()
                .name(dto.getServiceName())
                .serviceCycle("MONTH".equals(dto.getServiceCycle()) ? ServiceCycle.MONTH : ServiceCycle.WEEK)
                .availableDay(dto.getAvailableDay())
                .detailContents(dto.getDetailContents())
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

        Page<Services> serviceList = servicesRepository.findListWithJoinImage(storeId, pageRequest);

        List<ResServiceListDto> result = serviceList.stream()
                .map(m -> new ResServiceListDto(m, globalProperties.getFileUploadPath()))
                .collect(Collectors.toList());

        return new ListResponse(result, serviceList.getTotalElements());
    }

    /**
     * 판매자) 서비스 상세 조회
     */
    public ResStoreServiceDto getStoreServiceDetail(Long serviceId) {

        Optional<Services> serviceDetail = servicesRepository.findServiceDetail(serviceId);

        // category는 지연로딩으로 가져와야해서 여기서 dto까지 조회해서 보낸다.
        Set<ServiceCategory> serviceCategories = serviceDetail.orElseThrow(EntityNotFoundException::new)
                .getServiceCategories();
        List<ResCategoryDto> categoryDtos = serviceCategories.stream()
                .map(o -> ResCategoryDto.builder()
                        .categoryId(o.getCategory().getId())
                        .categoryName(o.getCategory().getName())
                        .build())
                .collect(Collectors.toList());

        return new ResStoreServiceDto(serviceDetail.orElseThrow(EntityNotFoundException::new), categoryDtos);
    }

}
