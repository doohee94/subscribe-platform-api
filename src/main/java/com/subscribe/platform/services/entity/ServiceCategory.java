package com.subscribe.platform.services.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "service_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceCategory {

    @Id @GeneratedValue
    @Column(name = "service_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="service_id")
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
