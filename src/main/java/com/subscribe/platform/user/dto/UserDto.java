package com.subscribe.platform.user.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

   @Data
   public static class CreateUserDto{

        private String email;
        private String password;
        private String name;
        private boolean isStore;

    }

}

