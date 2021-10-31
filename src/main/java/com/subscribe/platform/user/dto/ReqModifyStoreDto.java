package com.subscribe.platform.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReqModifyStoreDto {

    @NotBlank
    private String storeName;
    @NotBlank
    private String businessNum;

    @Builder
    public ReqModifyStoreDto(String storeName, String businessNum){
        this.storeName = storeName;
        this.businessNum = businessNum;
    }
}
