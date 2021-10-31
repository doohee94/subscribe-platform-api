package com.subscribe.platform.services.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.services.dto.ReqModifyServiceOptionDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "service_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceOption extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_option_id")
    private Long id;

    @Column(name = "option_name") @NotNull
    private String name;
    @ColumnDefault(value = "0")
    private int price;
    @ColumnDefault(value = "0")
    private int stock;  // 재고 수량(nullable)
    @ColumnDefault(value = "0")
    private int maxCount;   // 최대주문가능갯수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Services services;

    @Builder
    public ServiceOption(Long id, String name, int price, int stock, int maxCount){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.maxCount = maxCount;
    }

    public void setServices(Services services){
        this.services = services;
    }

    public void updateServiceOption(ReqModifyServiceOptionDto optionDto){
        this.name = optionDto.getOptionName();
        this.price = optionDto.getPrice();
        this.stock = optionDto.getStock();
        this.maxCount = optionDto.getMaxCount();
    }
}
