package com.subscribe.platform.subscribe.event;

import com.subscribe.platform.services.service.StoreServicesService;
import com.subscribe.platform.subscribe.dto.DeliveryDto;
import com.subscribe.platform.subscribe.entity.Delivery;
import com.subscribe.platform.subscribe.entity.Subscribe;
import com.subscribe.platform.subscribe.service.DeliveryService;
import com.subscribe.platform.subscribe.service.SubscribeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeliveryEventListener {

  private final DeliveryService deliveryService;
  private final SubscribeService subscribeService;
  private final StoreServicesService storeServicesService;

  @EventListener
  @Transactional
  public void saveDelivery(DeliveryEventDto deliveryDto){

    if(deliveryDto.getSubscribeIds().isEmpty()){
      return;
    }

    List<Subscribe> subscribes = subscribeService.findByIds(deliveryDto.getSubscribeIds());

    if(subscribes.isEmpty()){
      return;
    }

    List<Delivery> deliveries = subscribes.stream()
        .map(t->new DeliveryDto(t, t.getServices().getStore()))
        .flatMap(t->t.toEntities().stream())
        .collect(Collectors.toList());

    deliveryService.save(deliveries);
  }




}
