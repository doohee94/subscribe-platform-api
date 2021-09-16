package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.services.entity.Services;
import com.subscribe.platform.user.entity.Customer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscribe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subscribe extends BaseTimeEntity {

    // 구독시작일이 등록일

    @Id
    @Column(name = "subscribe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime cancelDate;
    private String cancelReason;

    private int payScheduledPrice;  // 결제예정금액
    private LocalDateTime payScheduledDate; // 결제예정일
    private String selectedOptions; // 선택한 옵션 아이디들 쉼표로 구분


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="service_id")
    private Services services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
