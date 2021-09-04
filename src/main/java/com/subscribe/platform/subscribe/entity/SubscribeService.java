package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.services.entity.Services;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscribeService {
    @Id @Column(name = "subscribe_service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int payScheduledPrice;  // 결제예정금액
    private LocalDateTime payScheduledDate; // 결제예정일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Services services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_id")
    private Subscribe subscribe;
}
