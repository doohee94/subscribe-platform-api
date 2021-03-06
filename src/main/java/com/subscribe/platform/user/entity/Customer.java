package com.subscribe.platform.user.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.user.dto.CustomerUpdateDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public Customer(Address address, Phone phone) {
        this.address = address;
        this.phone = phone;
    }

    public void setUser(User user) {
        this.user = user;
        user.setCustomer(this);
    }

    public void update(CustomerUpdateDto customerUpdateDto) {
        this.address = customerUpdateDto.getAddress();
        this.phone = customerUpdateDto.getPhone();
    }
}
