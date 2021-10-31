package com.subscribe.platform.user.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.services.entity.Review;
import com.subscribe.platform.subscribe.entity.PayInfo;
import com.subscribe.platform.subscribe.entity.Subscribe;
import com.subscribe.platform.user.dto.ReqModifyCustomerDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseTimeEntity {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Address address;

    @Embedded
    private Phone phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Subscribe> subscribes = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<PayInfo> payInfos = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();


    @Builder
    public Customer(Address address, Phone phone) {
        this.address = address;
        this.phone = phone;
    }

    public void setUser(User user) {
        this.user = user;
        user.setCustomer(this);
    }

    public void update(ReqModifyCustomerDto reqModifyCustomerDto) {
        this.address = reqModifyCustomerDto.getAddress();
        this.phone = reqModifyCustomerDto.getPhone();
    }

    public void addPayInfo(PayInfo payInfo){
        this.payInfos.add(payInfo);
        payInfo.setCustomer(this);
    }

}
