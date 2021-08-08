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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicesService {

    private final FileHandler fileHandler;
    private final GlobalProperties globalProperties;

    private final ServicesRepository servicesRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    private final ServiceOptionRepository serviceOptionRepository;
    private final ServiceImageRepository serviceImageRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServicesQuerydslRepository servicesQuerydslRepository;

    /**
     * 서비스 등록
     */
    @Transactional
    public void createService(CreateServiceDto dto) throws IOException {

        // 서비스 옵션 담기
        List<ServiceOption> serviceOptionList = new ArrayList<>();
        for (CreateServiceOptionDto serviceOptionDto : dto.getServiceOptions()) {
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
        for (CreateCategoryDto categoryDto : dto.getCategories()) {
            Optional<Category> category = categoryRepository.findById(categoryDto.getCategoryId());

            Optional.of(category).ifPresent((value) -> {
                categoryList.add(value.orElseThrow(EntityNotFoundException::new));
            });
        }

        // 서비스 이미지 담기
        List<ServiceImage> serviceImageList = new ArrayList<>();
        for (CreateServiceImageDto serviceImageDto : dto.getServiceImages()) {

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
     *
     * @return
     */
    public ListResponse getStoreServiceList(int pageNum, int size) {

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

    /**
     * 판매자) 서비스 수정
     */
    @Transactional
    public void updateService(UpdateServiceDto updateServiceDto) throws IOException {

        // 판매자 정보 조회

        // 서비스 조회
        Optional<Services> services = servicesRepository.findById(updateServiceDto.getServiceId());

        // 서비스 기본정보 수정
        services.orElseThrow(EntityNotFoundException::new)
                .updateServices(
                        updateServiceDto.getServiceName(),
                        "MONTH".equals(updateServiceDto.getServiceCycle()) ? ServiceCycle.MONTH : ServiceCycle.WEEK,
                        updateServiceDto.getAvailableDay(),
                        updateServiceDto.getDetailContents()
                );

        // 서비스 옵션 수정
        updateServiceDto.getServiceOptions()
                .forEach(
                        optionDto -> services.orElseThrow(EntityNotFoundException::new)
                                .updateServiceOption(optionDto)
                );

        // 카테고리 삭제
        services.orElseThrow(EntityNotFoundException::new)
                .deleteCategory();
        // 카테고리 수정
        List<Long> categoryIds = updateServiceDto.getCategories().stream()
                .map(o -> o.getCategoryId()).collect(Collectors.toList());

        List<Category> categories = categoryRepository.findByIdIn(categoryIds);
        if (categories.size() != 0) {
            updateServiceDto.getCategories()
                    .forEach(
                            categoryDto -> services.orElseThrow(EntityNotFoundException::new)
                                    .updateServiceCategory(categories)
                    );
        }

        // 서비스 이미지 수정
        if (updateServiceDto.getServiceImages() != null) {
            for (UpdateServiceImageDto serviceImage : updateServiceDto.getServiceImages()) {
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
    public void deleteServicesAdditionalResource(UpdateServiceDto updateServiceDto) throws FileNotFoundException {

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

    /**
     * 사용자) 서비스 리스트 조회(그냥 검색, 서비스이름 검색)
     */
    public ListResponse getSearchServiceList(String serviceName, int pageNum, int size) {

        // 페이징 정보
        PageRequest pageRequest = PageRequest.of(pageNum, size);

        Page<Services> result;
        if (serviceName.trim().isEmpty()) {  // 그냥 리스트 조회한 경우
            result = servicesRepository.findAllSearchList(pageRequest);
        } else {    // 서비스이름으로 조회한 경우
            result = servicesRepository.findSearchList(serviceName, pageRequest);
        }

        List<ResServiceListDto> list = result.stream()
                .map(o -> new ResServiceListDto(o, globalProperties.getFileUploadPath()))
                .collect(Collectors.toList());

        return new ListResponse(list, result.getTotalElements());
    }

    /**
     * 사용자) 카테고리별 서비스 리스트 검색
     */
    public ListResponse getServiceListByCategory(Long categoryId, int pageNum, int size){
        PageRequest pageRequest = PageRequest.of(pageNum, size);    // 페이징 정보
        Page<ResServiceListDto> result = servicesQuerydslRepository.findServicesByCategory(categoryId, pageRequest);

        // 이미지 파일 경로 붙이기
        result.forEach(
                o -> o.setThumbnailImage(globalProperties.getFileUploadPath()+o.getThumbnailImage())
        );
        return new ListResponse(result.getContent(), result.getTotalElements());
    }

    /**
     * 사용자) 신상서비스 조회 : 최근 3일내에 등록된 서비스 조회
     */
    public ListResponse getNewServiceList(int pageNum, int size){
        PageRequest pageRequest = PageRequest.of(pageNum, size);

        LocalDateTime toDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime fromDate = toDate.minusDays(3);
        Page<Services> result = servicesRepository.findByCreatedDateBetween(fromDate, toDate, pageRequest);

        log.debug("nowDate = {}", toDate);

        List<ResServiceListDto> list = result.stream()
                .map(o -> new ResServiceListDto(o, globalProperties.getFileUploadPath()))
                .collect(Collectors.toList());

        return new ListResponse(list, result.getTotalElements());
    }
}
