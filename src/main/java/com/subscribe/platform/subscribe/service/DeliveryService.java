package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.subscribe.entity.Delivery;
import com.subscribe.platform.subscribe.repository.DeliveryRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;

  public void save(List<Delivery> deliveries) {
    deliveryRepository.saveAll(deliveries);
  }
}
