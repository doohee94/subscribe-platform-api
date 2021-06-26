package com.subscribe.platform.services.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "service_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceOption {

    @Id @GeneratedValue
    @Column(name = "service_option_id")
    private Long id;

    @Column(name = "option_name") @NotNull
    private String name;
    @NotNull
    private int price;
    private int stock;  // 재고 수량(nullable)
    private int maxCount;   // 최대주문가능갯수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Services services;

    @Builder
    public ServiceOption(String name, int price, int stock, int maxCount){
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.maxCount = maxCount;
    }

    public void setServices(Services services){
        this.services = services;
    }
}
