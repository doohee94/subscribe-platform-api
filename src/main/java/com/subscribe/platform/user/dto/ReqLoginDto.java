package com.subscribe.platform.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReqLoginDto {

    @NotBlank
//    @Email
    private String email;
    @NotBlank
    private String password;

    public ReqLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
