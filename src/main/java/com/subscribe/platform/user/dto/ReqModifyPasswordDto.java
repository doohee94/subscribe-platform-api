package com.subscribe.platform.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqModifyPasswordDto {

    @NotBlank
    private String currPassword;    // 현재 비밀번호
    @NotBlank
    private String changePassword;  // 변경될 비밀번호
}
