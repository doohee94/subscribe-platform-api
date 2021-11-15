package com.subscribe.platform.user.controller;

import com.subscribe.platform.user.dto.ReqModifyPasswordDto;
import com.subscribe.platform.user.dto.ReqModifyStoreDto;
import com.subscribe.platform.user.dto.ResUserDto;
import com.subscribe.platform.user.dto.UserDto;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ApiOperation("회원가입")
    public void createUser(@Validated @RequestBody UserDto.CreateUserDto createUserDto){

        userService.createUser(createUserDto);
    }

    @GetMapping("/email-dupl")
    @ApiOperation("이메일 중복확인")
    public Map checkEmailDupl(@RequestParam(value = "email") String email) {
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
    @GetMapping("/store/storeinfo")
    @ApiOperation("판매자 정보 조회")
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
    @PatchMapping("/store/storeinfo-form")
    @ApiOperation("판매자 정보 수정")
    public void updateStoreinfo(@Valid @RequestBody ReqModifyStoreDto request){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();    // 나중에 변경 예정
        userService.updateStore(email, request);
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/password")
    @ApiOperation("비밀번호 변경")
    public boolean updatePassword(@Valid @RequestBody ReqModifyPasswordDto passwordDto){

        return userService.updatePassword(passwordDto);
    }
}
