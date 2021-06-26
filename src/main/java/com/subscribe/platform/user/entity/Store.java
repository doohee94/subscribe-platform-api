package com.subscribe.platform.user.entity;

import com.subscribe.platform.services.entity.Services;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Services> servicesList = new ArrayList<>();

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

    public void updateStore(String storeName, String businessNum){
        this.storeName = storeName;
        this.businessNum = businessNum;
    }

//    public void setServiceList(Services services){
//        this.servicesList.add(services);
//    }
}
