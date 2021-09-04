package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.user.entity.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscribe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscribe extends BaseTimeEntity {

    @Id
    @Column(name = "subscribe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Enumerated(EnumType.STRING)
//    private ServiceDay serviceDay;
//
//    private LocalDateTime serviceDate;


    private Status status;
    private LocalDateTime cancelDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL)
    private Set<SubscribeService> subscribeServices = new HashSet<>();

}
