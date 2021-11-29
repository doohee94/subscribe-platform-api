package com.subscribe.platform.subscribe.event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class DeliveryEventDto {

  private List<Long> subscribeIds;

  public DeliveryEventDto(String ids) {
    this.subscribeIds = Arrays.stream(ids.split(","))
        .map(Long::parseLong)
        .collect(Collectors.toList());
  }
}
