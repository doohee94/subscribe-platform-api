package com.subscribe.platform.services.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.services.dto.UpdateServiceCategoryDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "service_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceCategory extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="service_id")
    private Services services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setServices(Services services){
        this.services = services;
    }
    private void setCategory(Category category){
        this.category = category;
    }
    // 생성 메소드
    public static ServiceCategory createServiceCategory(Services services, Category category){
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setCategory(category);
        serviceCategory.setServices(services);
        return serviceCategory;
    }

}
