package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import com.subscribe.platform.user.entity.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pay_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PayInfo extends BaseTimeEntity {

    @Id @Column(name = "pay_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creditCardCompany;
    private String cardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "payInfo", cascade = CascadeType.ALL)
    private Set<Subscribe> subscribes = new HashSet<>();

    // ===== 연관관계 메소드 =====
    public void addSubscribe(Subscribe subscribe) {
        this.subscribes.add(subscribe);
        subscribe.setPayInfo(this);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Builder
    public PayInfo(String creditCardCompany, String cardNo, Subscribe subscribe) {
        this.creditCardCompany = creditCardCompany;
        this.cardNo = cardNo;
        addSubscribe(subscribe);
    }

    // 저장된 정보인지 확인
    public boolean isSavedInfo(String cardNo, String creditCardCompany){
        return this.cardNo.equals(cardNo) && this.creditCardCompany.equals(creditCardCompany);
    }
}
