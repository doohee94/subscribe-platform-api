package com.subscribe.platform.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateStoreDto {
    private String storeName;
    private String businessNum;

    @Builder
    public UpdateStoreDto(String storeName, String businessNum){
        this.storeName = storeName;
        this.businessNum = businessNum;
    }
}
