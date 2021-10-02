package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.services.entity.ServiceCycle;
import com.subscribe.platform.services.entity.Services;
import com.subscribe.platform.user.entity.Customer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscribe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subscribe extends BaseTimeEntity {

    @Id
    @Column(name = "subscribe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private ServiceCycle deliveryCycle; // 배송주기 : WEEK, MONTH
    private String deliveryDay; // 배송일 : 요일 or 날짜

    private LocalDateTime cancelDate;
    private Long cancelReasonId;  // 구독취소사유코드
    private String cancelReasonEtc; // 취소사유 기타

    private int payScheduledPrice;  // 결제예정금액
    private LocalDateTime payScheduledDate; // 결제예정일

    private LocalDateTime subscribeStartDate;   // 구독시작일
    private LocalDateTime shoppingDate; // 장바구니에 넣은날

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL)
    private Set<PickedOption> pickedOptions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_info_id")
    private PayInfo payInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="service_id")
    private Services services;


    // ============== 편의 메소드 ==============
    public void cancelSubscribe(Long cancelReasonId, String cancelReasonEtc){
        this.status = Status.CANCEL;
        this.cancelDate = LocalDateTime.now();
        this.cancelReasonId = cancelReasonId;
        this.cancelReasonEtc = cancelReasonEtc;
    }

    public void startSubscribe(){
        this.status = Status.SUBSCRIBE;
        this.subscribeStartDate = LocalDateTime.now();
    }

    public void setPayInfo(PayInfo payInfo){
        this.payInfo = payInfo;
    }



}
