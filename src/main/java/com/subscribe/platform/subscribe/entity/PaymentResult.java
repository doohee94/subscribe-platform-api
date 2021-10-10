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

    @Id @Column(name = "payment_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PayStatus status;   // 결제상태 결제 or 취소
    private Long ownerId;   // 결제자 id(customerId
    private String creditCardCompany;   // 카드사
    private String paidCardNo;  // 카드번호
    private int payPrice;   // 결제금액
    private String subscribes;  // 결제된 구독 아이디들(,로 구분)

    @Builder
    public PaymentResult(PayStatus status, Long ownerId, String creditCardCompany, String paidCardNo, int payPrice, String subscribes) {
        this.status = status;
        this.ownerId = ownerId;
        this.creditCardCompany = creditCardCompany;
        this.paidCardNo = paidCardNo;
        this.payPrice = payPrice;
        this.subscribes = subscribes;
    }
}
