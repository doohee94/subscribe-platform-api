package com.subscribe.platform.services.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.services.dto.*;
import com.subscribe.platform.user.entity.Customer;
import com.subscribe.platform.user.entity.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "service")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Services extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;

    @Column(name = "service_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private ServiceCycle serviceCycle;

    private String availableDay; // 판매자가 가능한 배송일, 요일 (쉼표로 구분)

    // 이건 서비스테이블이 아니라 구독테이블에 들어가야할거같음
//    @Enumerated(EnumType.STRING)
//    private ServiceDay serviceDay;
//
//    private LocalDateTime serviceDate;

    @Lob
    private String detailContents;

    // 판매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // 서비스 옵션
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Set<ServiceOption> serviceOptions = new HashSet<>();

    // 카테고리
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Set<ServiceCategory> serviceCategories = new HashSet<>();

    // 서비스 이미지
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Set<ServiceImage> serviceImages = new HashSet<>();

    // ===== 연관관계 메소드 모음 =====
    public void setStore(Store store) {
        this.store = store;
//        store.setServiceList(this);
    }

    public void addServiceOption(ServiceOption serviceOption) {
        serviceOptions.add(serviceOption);
        serviceOption.setServices(this);
    }

    public void addServiceImage(ServiceImage serviceImage) {
        serviceImages.add(serviceImage);
        serviceImage.setServices(this);
    }

    public void addServiceCategory(Category category) {
        ServiceCategory serviceCategory = ServiceCategory.createServiceCategory(this, category);
        serviceCategories.add(serviceCategory);
    }
    // =============================

    @Builder
    public Services(Long id, String name, ServiceCycle serviceCycle, String availableDay, String detailContents, List<ServiceOption> serviceOptions, List<ServiceImage> serviceImages, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.serviceCycle = serviceCycle;
        this.availableDay = availableDay;
        this.detailContents = detailContents;

        if (serviceOptions != null) {
            for (ServiceOption serviceOption : serviceOptions) {
                addServiceOption(serviceOption);
            }
        }

        if (serviceImages != null) {
            for (ServiceImage serviceimage : serviceImages) {
                addServiceImage(serviceimage);
            }
        }

        if (categories != null) {
            for (Category category : categories) {
                addServiceCategory(category);
            }
        }
    }

    public void updateServices(String name, ServiceCycle serviceCycle, String availableDay, String detailContents){
        this.name = name;
        this.serviceCycle = serviceCycle;
        this.availableDay = availableDay;
        this.detailContents = detailContents;
    }

    public void updateServiceOption(UpdateServiceOptionDto optionDto) {
        if(optionDto.getServiceOptionId() == null){
            addServiceOption(ServiceOption.builder()
                    .name(optionDto.getOptionName())
                    .price(optionDto.getPrice())
                    .stock(optionDto.getStock())
                    .maxCount(optionDto.getMaxCount())
                    .build()
            );
        }else{
            this.serviceOptions.stream()
                    .filter(o -> o.getId() == optionDto.getServiceOptionId())
                    .forEach(o -> o.updateServiceOption(optionDto));
        }
    }

    public void updateServiceCategory(List<Category> categories){
        for (Category category : categories) {
            addServiceCategory(category);
        }
    }

    public void deleteCategory(){
        this.serviceCategories.clear();
    }

    public void updateServiceImage(UpdateServiceImageDto imageDto) {
        if (imageDto.getServiceImageId() == null) {
            addServiceImage(ServiceImage.builder()
                    .imageType("THUMBNAIL".equals(imageDto.getImageType()) ? ImageType.THUMBNAIL : ImageType.DETAIL)
                    .imageSeq(imageDto.getImageSeq())
                    .name(imageDto.getFileInfo().getOriginName())
                    .fakeName(imageDto.getFileInfo().getFakeName())
                    .extensionName(imageDto.getFileInfo().getExtensionName())
                    .build()
            );
        }else{
            serviceImages.stream()
                    .filter(o -> o.getId() == imageDto.getServiceImageId())
                    .forEach(o -> o.updateServiceImage(imageDto));
        }
    }

}
