package com.subscribe.platform.user.entity;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;
    private String storeName;
    private String businessNum;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user){
        this.user = user;
    }

    @Builder
    public Store(String storeName, String businessNum){

        Assert.notNull(storeName,"storeName must not be null");
        Assert.notNull(businessNum,"businessNum must not be null");

        this.storeName = storeName;
        this.businessNum = businessNum;
    }
}
