package com.subscribe.platform.user.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Password {

    private String password;

    public boolean isPasswordMatches(String password) {
        return this.password.equals(password);
    }
}
