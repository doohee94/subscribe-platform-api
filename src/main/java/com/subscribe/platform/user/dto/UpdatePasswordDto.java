package com.subscribe.platform.user.dto;

import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String currPassword;    // 현재 비밀번호
    private String changePassword;  // 변경될 비밀번호
}
