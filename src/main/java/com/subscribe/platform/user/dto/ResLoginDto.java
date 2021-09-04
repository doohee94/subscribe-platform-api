package com.subscribe.platform.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ResLoginDto {
    private String token;
    private List<String> auths;
}
