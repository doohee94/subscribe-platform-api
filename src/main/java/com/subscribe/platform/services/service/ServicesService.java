package com.subscribe.platform.services.service;

import com.subscribe.platform.common.handler.FileHandler;
import com.subscribe.platform.common.model.FileInfo;
import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.common.model.SelectBox;
import com.subscribe.platform.common.properties.GlobalProperties;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.services.entity.*;
import com.subscribe.platform.services.repository.*;
import com.subscribe.platform.user.entity.Customer;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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

    private final UserRepository userRepository;
    private final ServicesRepository servicesRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    private final ServicesQuerydslRepository servicesQuerydslRepository;

    /**
     * 사용자) 서비스 리스트 조회(그냥 검색, 서비스이름 검색)
     */
    public List getSearchServiceList(String serviceName, PageRequest pageRequest) {

//        // 페이징 정보
//        PageRequest pageRequest = PageRequest.of(pageNum, size);

        Page<Services> result;
        if (serviceName.trim().isEmpty()) {  // 그냥 리스트 조회한 경우
            result = servicesRepository.findAllSearchList(pageRequest);
        } else {    // 서비스이름으로 조회한 경우
            result = servicesRepository.findSearchList(serviceName, pageRequest);
        }

        return result.stream()
                .map(o -> new ResServiceListDto(o, globalProperties.getFileUploadPath()))
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 조회
     */
    public List<SelectBox> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new SelectBox(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    /**
     * 사용자) 카테고리별 서비스 리스트 조회
     */
    public ListResponse getServiceListByCategory(Long categoryId, PageRequest pageRequest) {
        Page<ResServiceListDto> result = servicesQuerydslRepository.findServicesByCategory(categoryId, pageRequest);

        // 이미지 파일 경로 붙이기
        result.forEach(
                o -> o.setThumbnailImage(globalProperties.getFileUploadPath() + o.getThumbnailImage())
        );
        return new ListResponse(result.getContent(), result.getTotalElements());
    }

    /**
     * 사용자) 신상서비스 조회 : 최근 3일내에 등록된 서비스 조회
     */
    public ListResponse getNewServiceList(PageRequest pageRequest) {

        LocalDateTime toDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime fromDate = toDate.minusDays(3);
        Page<Services> result = servicesRepository.findByCreatedDateBetween(fromDate, toDate, pageRequest);

        log.debug("nowDate = {}", toDate);

        List<ResServiceListDto> list = result.stream()
                .map(o -> new ResServiceListDto(o, globalProperties.getFileUploadPath()))
                .collect(Collectors.toList());

        return new ListResponse(list, result.getTotalElements());
    }

    /**
     * 사용자) 후기작성
     */
    public void writeReview(ReqRegistReviewDto reviewDto) throws IOException {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = userRepository.findByEmail(email).getCustomer();

        /**
         * validation
         * 1. 해당소비자가 서비스 구독하고 있는지 여부,
         * 2. 배송(서비스)일로부터 일주일 지난 후 (지금 못함)
         */

        // reviewImage 만들기
        List<ReviewImage> files = new ArrayList<>();
        if (reviewDto.getImageFiles() != null && reviewDto.getImageFiles().size() != 0) {
            List<MultipartFile> imageFiles = reviewDto.getImageFiles();
            for (MultipartFile imageFile : imageFiles) {
                FileInfo fileInfo = fileHandler.getFileInfo(imageFile);
                files.add(ReviewImage.builder()
                        .name(fileInfo.getOriginName())
                        .fakeName(fileInfo.getFakeName())
                        .extensionName(fileInfo.getExtensionName())
                        .build());
            }
        }

        // service
        Services service = servicesRepository.findById(reviewDto.getServiceId())
                .orElseThrow(EntityNotFoundException::new);

        Review review = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .reviewImages(files)
                .customer(customer)
                .services(service)
                .build();

        reviewRepository.save(review);
    }
}
