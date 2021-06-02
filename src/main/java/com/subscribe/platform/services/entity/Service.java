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
public class Service extends BaseTimeEntity{

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

    @Builder
    public Service(String name, ServiceCycle serviceCycle, String availableDay, List<ServiceOption> serviceOptions, List<ServiceImage> serviceImages, List<ServiceCategory> serviceCategories){
        this.name = name;
        this.serviceCycle = serviceCycle;
        this.availableDay = availableDay;

        for(ServiceOption serviceOption : serviceOptions){
            this.addServiceOption(serviceOption);
        }
        for(ServiceImage serviceImage : serviceImages){
            this.addServiceImage(serviceImage);
        }
        for(ServiceCategory serviceCategory : serviceCategories){
            this.addServiceCategory(serviceCategory);
        }
    }

    // ===== 연관관계 메소드 =====
    public void addServiceOption(ServiceOption serviceOption){
        serviceOptions.add(serviceOption);
        serviceOption.setService(this);
    }

    public void addServiceImage(ServiceImage serviceImage){
        serviceImages.add(serviceImage);
        serviceImage.setServiceImage(this);
    }

    public void addServiceCategory(ServiceCategory serviceCategory){
        serviceCategorys.add(serviceCategory);
        serviceCategory.setService(this);
    }
    // ========================

    // 서비스 생성 메소드
//    public Service createService(List<ServiceOption> serviceOptions, List<ServiceImage> serviceImages, List<ServiceCategory> serviceCategories){
//        Service service = new Service();
//        for(ServiceOption serviceOption : serviceOptions){
//            service.addServiceOption(serviceOption);
//        }
//        for(ServiceImage serviceImage : serviceImages){
//            service.addServiceImage(serviceImage);
//        }
//        for(ServiceCategory serviceCategory : serviceCategories){
//            service.addServiceCategory(serviceCategory);
//
//        return service;
//    }
}
