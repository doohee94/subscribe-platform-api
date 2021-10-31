package com.subscribe.platform.services.entity;

import com.subscribe.platform.user.entity.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String title;   // 제목
    @Column(length = 4000)
    private String content; // 내용
    @ColumnDefault("0")
    private Integer viewCount;  // 조회수

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private Set<ReviewImage> reviewImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Services services;

    @Builder
    public Review(String title, String content, List<ReviewImage> reviewImages, Customer customer, Services services){
        this.title = title;
        this.content = content;
        reviewImages.stream().forEach(
                image -> {
                    this.reviewImages.add(image);
                    image.setReview(this);
                }
        );
        addCustomer(customer);
        addServices(services);
    }

    public void addCustomer(Customer customer){
        this.customer = customer;
        customer.getReviews().add(this);
    }

    public void addServices(Services services){
        this.services = services;
        services.getReviews().add(this);
    }
}
