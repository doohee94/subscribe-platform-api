package com.subscribe.platform.user.entity;

import lombok.Getter;

@Getter
public enum UserStatus {
    ADMIN("admin")
    ;

    private String test;

    UserStatus(String test) {
        this.test = test;
    }


}
