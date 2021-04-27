package com.subscribe.platform.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private String password;

    public Password(String password) {
        this.password = password;
    }

    public boolean isPasswordMatches(String password) {
        return this.password.equals(password);
    }
}
