package com.subscribe.platform.services.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Services extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "service_id")
    private Long id;

    @Column(name = "service_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private ServiceCycle serviceCycle;

    private String availableDay; // 판매자가 가능한 배송일, 요일 (쉼표로 구분)

    @Enumerated(EnumType.STRING)
    private ServiceDay serviceDay;

    private LocalDateTime serviceDate;

    @Embedded
    private DetailContents detailContents;

   // 서비스 옵션
    @OneToMany(mappedBy = "service")
    private List<ServiceOption> serviceOptions = new ArrayList<>();

    // 카테고리
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    List<ServiceCategory> serviceCategorys = new ArrayList<>();

    // 서비스 이미지
    @OneToMany(mappedBy = "service")
    List<ServiceImage> serviceImages = new ArrayList<>();

    // ===== 연관관계 메소드 모음 =====
    public void addServiceOption(ServiceOption serviceOption){
        serviceOptions.add(serviceOption);
        serviceOption.setServices(this);
    }

    public void addServiceImage(ServiceImage serviceImage){
        serviceImages.add(serviceImage);
        serviceImage.setServices(this);
    }

    public void addServiceCategory(ServiceCategory serviceCategory){
        serviceCategorys.add(serviceCategory);
        serviceCategory.setServices(this);
    }
    // =============================

    @Builder
    public Services(String name, ServiceCycle serviceCycle, String availableDay, List<ServiceOption> serviceOptions, List<ServiceImage> serviceImages, List<ServiceCategory> serviceCategories){
        this.name = name;
        this.serviceCycle = serviceCycle;
        this.availableDay = availableDay;
        for(ServiceOption serviceOption : serviceOptions){
            addServiceOption(serviceOption);
        }
        for(ServiceImage serviceimage : serviceImages){
            addServiceImage(serviceimage);
        }
        for(ServiceCategory serviceCategory : serviceCategories){
            addServiceCategory(serviceCategory);
        }
    }
}
