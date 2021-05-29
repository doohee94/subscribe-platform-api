package com.subscribe.platform.user.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Phone {

    private String phoneNumber;
}
