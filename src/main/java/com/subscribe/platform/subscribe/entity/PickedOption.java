package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "picked_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PickedOption extends BaseTimeEntity {

    @Id @Column(name = "picked_option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long optionId;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_id")
    private Subscribe subscribe;

    @Builder
    public PickedOption(Long optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }

    public void updateSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }
}
