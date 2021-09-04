package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.UpdatePasswordDto;
import com.subscribe.platform.user.dto.UpdateStoreDto;
import com.subscribe.platform.user.dto.ResUserDto;
import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public void test(String email) {

        System.out.println(userService.findByEmail(email));
    }

    @PostMapping("/sign-up")
    public void createUser(@RequestBody UserDto.CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
    }

    @GetMapping("/checkEmailDupl")
    public Map checkEmailDupl(String email) {
        return userService.findCheckByEmail(email);
    }

    @GetMapping("/{id}")
    public String getUserEmail(@PathVariable long id) {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * 판매자 정보 조회
     * @return
     */
    @GetMapping("/store/getStoreinfo")
    public ResUserDto getStoreInfo(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();    // 나중에 변경 예정
        User result = userService.findByEmail(email);
        return ResUserDto.builder()
                .email(result.getEmail())
                .name(result.getName())
                /*.password(result.getPassword().getPassword())*/
                .storeName(result.getStore().getStoreName())
                .businessNum(result.getStore().getBusinessNum())
                .build();
    }

    // restApi 사용에서 store(엔티티)정보를 모두 가지고 있으면 put을 쓰고 엔티티의 일부만 수정한다 하면 patch를 쓴다

    /**
     * 판매자 정보 수정
     * @param request
     */
    // put patch 등은 @RequestBody로 해서 body를 받아야한다.
    @PutMapping("/store/updateStoreinfo")
    public void updateStoreinfo(@Valid @RequestBody UpdateStoreDto request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();    // 나중에 변경 예정
        userService.updateStore(email, request);
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/password")
    public boolean updatePassword(@RequestBody UpdatePasswordDto passwordDto){
        return userService.updatePassword(passwordDto);
    }
}
