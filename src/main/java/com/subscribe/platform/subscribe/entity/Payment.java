package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.user.entity.Customer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment extends BaseTimeEntity {

    // 등록일이 결제일

    @Id @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paidCardNo;
    private int payPrice;

    private LocalDateTime cancelDate;
    private String cancelReason;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private Set<Subscribe> subscribes = new HashSet<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
}
