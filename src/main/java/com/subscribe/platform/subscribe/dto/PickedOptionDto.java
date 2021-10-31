package com.subscribe.platform.subscribe.dto;

import com.subscribe.platform.subscribe.entity.PickedOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickedOptionDto {

    private Long optionId;
    private int quantity;

    public PickedOption of() {
        return PickedOption.builder()
                .optionId(optionId)
                .quantity(quantity)
                .build();

    }
}
