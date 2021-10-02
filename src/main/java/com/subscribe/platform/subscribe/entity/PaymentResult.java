package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentResult extends BaseTimeEntity {

    // 등록일이 결제일

    @Id @Column(name = "payment_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PayStatus status;
    private Long ownerId;
    private String creditCardCompany;
    private String paidCardNo;
    private int payPrice;

    @Builder
    public PaymentResult(PayStatus status, Long ownerId, String creditCardCompany, String paidCardNo, int payPrice) {
        this.status = status;
        this.ownerId = ownerId;
        this.creditCardCompany = creditCardCompany;
        this.paidCardNo = paidCardNo;
        this.payPrice = payPrice;
    }
}
