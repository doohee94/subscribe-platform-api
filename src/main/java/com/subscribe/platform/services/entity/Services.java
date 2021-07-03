package com.subscribe.platform.services.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.user.entity.Customer;
import com.subscribe.platform.user.entity.Store;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<ServiceOption> serviceOptions = new ArrayList<>();

    // 카테고리
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    List<ServiceCategory> serviceCategorys = new ArrayList<>();

    // 서비스 이미지
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    List<ServiceImage> serviceImages = new ArrayList<>();

    // ===== 연관관계 메소드 모음 =====
    public void setStore(Store store){
        this.store = store;
//        store.setServiceList(this);
    }

    public void addServiceOption(ServiceOption serviceOption){
        serviceOptions.add(serviceOption);
        serviceOption.setServices(this);
    }

    public void addServiceImage(ServiceImage serviceImage){
        serviceImages.add(serviceImage);
        serviceImage.setServices(this);
    }

    public void addServiceCategory(Category category){
        ServiceCategory serviceCategory = ServiceCategory.createServiceCategory(this, category);
        serviceCategorys.add(serviceCategory);
    }
    // =============================

    @Builder
    public Services(String name, ServiceCycle serviceCycle, String availableDay, String detailContents, List<ServiceOption> serviceOptions, List<ServiceImage> serviceImages, List<Category> categories){
        this.name = name;
        this.serviceCycle = serviceCycle;
        this.availableDay = availableDay;
        this.detailContents = detailContents;

        for(ServiceOption serviceOption : serviceOptions){
            addServiceOption(serviceOption);
        }
        for(ServiceImage serviceimage : serviceImages){
            addServiceImage(serviceimage);
        }
        for(Category category : categories){
            addServiceCategory(category);
        }
    }
}
