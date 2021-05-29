package com.subscribe.platform.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResUserDto {
    private String email;
    private String password;
    private String name;
    private String storeName;
    private String businessNum;

    @Builder
    public ResUserDto(String email, String password, String name, String storeName, String businessNum){
        this.email = email;
        this.password = password;
        this.name = name;
        this.storeName = storeName;
        this.businessNum = businessNum;
    }
}
