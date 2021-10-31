package com.subscribe.platform.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

   @Data
   public static class CreateUserDto{

       @NotBlank
       @Email
        private String email;
       @NotBlank
        private String password;
       @NotBlank
        private String name;
        private boolean isStore;
        @NotBlank
        private String storeName;
        @NotBlank
        private String businessNum;
    }
}

