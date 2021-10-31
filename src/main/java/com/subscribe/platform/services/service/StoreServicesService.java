package com.subscribe.platform.services.service;

import com.subscribe.platform.common.handler.FileHandler;
import com.subscribe.platform.common.model.FileInfo;
import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.common.properties.GlobalProperties;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.entity.*;
import com.subscribe.platform.services.repository.*;
import com.subscribe.platform.user.entity.Store;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.StoreRepository;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServicesService {

    private final FileHandler fileHandler;
    private final GlobalProperties globalProperties;

    private final ServicesRepository servicesRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    private final ServiceOptionRepository serviceOptionRepository;
    private final ServiceImageRepository serviceImageRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;

    /**
     * 서비스 등록
     */
    @Transactional
    public void createService(ReqRegistServiceDto dto) throws IOException {

        // 서비스 옵션 담기
        List<ServiceOption> serviceOptionList = new ArrayList<>();
        if(dto.getServiceOptions() != null){
            for (ReqRegistServiceOptionDto serviceOptionDto : dto.getServiceOptions()) {
                ServiceOption option = ServiceOption.builder()
                        .name(serviceOptionDto.getOptionName())
                        .price(serviceOptionDto.getPrice())
                        .stock(serviceOptionDto.getStock())
                        .maxCount(serviceOptionDto.getMaxCount())
                        .build();
                serviceOptionList.add(option);
            }
        }

        // 서비스 카테고리 담기
        List<Category> categoryList = new ArrayList<>();
        if(dto.getCategories() != null){
            for (ReqRegistCategoryDto categoryDto : dto.getCategories()) {
                Optional<Category> category = categoryRepository.findById(categoryDto.getCategoryId());

                Optional.of(category).ifPresent((value) -> {
                    categoryList.add(value.orElseThrow(EntityNotFoundException::new));
                });
            }
        }

        // 서비스 이미지 담기
        List<ServiceImage> serviceImageList = new ArrayList<>();
        if(dto.getServiceImages() != null){
            for (ReqRegistServiceImageDto serviceImageDto : dto.getServiceImages()) {

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
     *
     * @return
     */
    public ListResponse getStoreServiceList(PageRequest pageRequest) {

//        // 페이징 정보
//        PageRequest pageRequest = PageRequest.of(pageNum, size);

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

    /**
     * 판매자) 서비스 수정
     */
    @Transactional
    public void updateService(ReqModifyServiceDto reqModifyServiceDto) throws IOException {

        // 판매자 정보 조회

        // 서비스 조회
        Optional<Services> services = servicesRepository.findById(reqModifyServiceDto.getServiceId());

        // 서비스 기본정보 수정
        services.orElseThrow(EntityNotFoundException::new)
                .updateServices(
                        reqModifyServiceDto.getServiceName(),
                        "MONTH".equals(reqModifyServiceDto.getServiceCycle()) ? ServiceCycle.MONTH : ServiceCycle.WEEK,
                        reqModifyServiceDto.getAvailableDay(),
                        reqModifyServiceDto.getDetailContents()
                );

        // 서비스 옵션 수정
        reqModifyServiceDto.getServiceOptions()
                .forEach(
                        optionDto -> services.orElseThrow(EntityNotFoundException::new)
                                .updateServiceOption(optionDto)
                );

        // 카테고리 삭제
        services.orElseThrow(EntityNotFoundException::new)
                .deleteCategory();
        // 카테고리 수정
        List<Long> categoryIds = reqModifyServiceDto.getCategories().stream()
                .map(o -> o.getCategoryId()).collect(Collectors.toList());

        List<Category> categories = categoryRepository.findByIdIn(categoryIds);
        if (categories.size() != 0) {
            reqModifyServiceDto.getCategories()
                    .forEach(
                            categoryDto -> services.orElseThrow(EntityNotFoundException::new)
                                    .updateServiceCategory(categories)
                    );
        }

        // 서비스 이미지 수정
        if (reqModifyServiceDto.getServiceImages() != null) {
            for (ReqModifyServiceImageDto serviceImage : reqModifyServiceDto.getServiceImages()) {
                serviceImage.setFileInfo(fileHandler.getFileInfo(serviceImage.getImageFile()));

                services.orElseThrow(EntityNotFoundException::new)
                        .updateServiceImage(serviceImage);
            }
        }
    }

    /**
     * 판매자) 서비스 수정 시 관련 옵션, 카테고리, 이미지 삭제
     * (리스트에서 삭제해도 변경감지가 안됨... 그래서 일부러 삭제해줌)
     */
    @Transactional
    public void deleteServicesAdditionalResource(ReqModifyServiceDto updateServiceDto) throws FileNotFoundException {

        Long serviceId = updateServiceDto.getServiceId();
        // 옵션 삭제
        List<Long> optionIds = updateServiceDto.getServiceOptions().stream()
                .filter(i -> i.getServiceOptionId() != null)
                .map(i -> i.getServiceOptionId()).collect(Collectors.toList());
        serviceOptionRepository.deleteByServices_IdAndIdNotIn(serviceId, optionIds);

        // 카테고리 삭제
        serviceCategoryRepository.deleteByServices_Id(serviceId);

        // 디비 이미지 삭제 전 삭제할 이미지파일정보 미리 불러오기
        List<ServiceImage> images = serviceImageRepository.findByServices_Id(serviceId);
        // 이미지 삭제
        List<Long> imageIds = updateServiceDto.getServiceImages().stream()
                .filter(o -> o.getServiceImageId() != null)
                .map(o -> o.getServiceImageId()).collect(Collectors.toList());
        serviceImageRepository.deleteByServices_IdAndIdNotIn(serviceId, imageIds);

        // 저장된 이미지파일 삭제
        for (ServiceImage image : images) {
            boolean deleteRes = fileHandler.deleteFile(image.getFakeName() + image.getExtensionName());
            if (!deleteRes) {
                throw new FileNotFoundException("fail to delete image file");
            }
        }
    }

    /**
     * 판매자) 서비스 삭제
     */
    @Transactional
    public void deleteService(Long serviceId) throws FileNotFoundException {

        // 저장된 이미지파일 삭제
        List<ServiceImage> images = serviceImageRepository.findByServices_Id(serviceId);
        for (ServiceImage image : images) {
            boolean deleteRes = fileHandler.deleteFile(image.getFakeName() + image.getExtensionName());
            if (!deleteRes) {
                throw new FileNotFoundException("fail to delete image file");
            }
        }

        // storeId 가져와서 일치하는 레코드 삭제해주기
        servicesRepository.deleteById(serviceId);
    }
}
