package com.subscribe.platform.subscribe.entity;

import com.subscribe.platform.user.entity.Store;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_id")
  private Long id;

  private LocalDate deliveryDate;
  private boolean isComplete;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "subscribe_id")
  private Subscribe subscribe;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "store_id")
  private Store store;

  @Builder
  public Delivery(LocalDate deliveryDate, Subscribe subscribe, Store store) {
    this.deliveryDate = deliveryDate;
    this.isComplete = false;
    this.subscribe = subscribe;
    this.store = store;
  }
}
