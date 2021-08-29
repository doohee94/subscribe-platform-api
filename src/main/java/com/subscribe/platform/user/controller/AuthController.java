package com.subscribe.platform.user.controller;

import com.subscribe.platform.config.securityAndJwtConfig.util.JwtUtil;
import com.subscribe.platform.user.dto.LoginDto;
import com.subscribe.platform.user.dto.ResLoginDto;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResLoginDto login(@RequestBody LoginDto loginDto) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        if (authenticate.isAuthenticated()) { // 권한이 있는 경우
            ResLoginDto response = new ResLoginDto();
            response.setToken(jwtUtil.generateToken(authenticate.getName(), (List<GrantedAuthority>) authenticate.getAuthorities()));
            response.setAuths(authenticate.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList()));
            return response;
        }

        return null;
    }

    /**
     * 로그인 여부 확인
     */
    @GetMapping("/confirm-login")
    public ResLoginDto confirmLogin(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        if (token != null) {
            String email = jwtUtil.extractUsername(token);
            List<String> authorities = userService.getAuthoritiesByEmail(email);
            ResLoginDto response = new ResLoginDto();
            response.setToken(token);
            response.setAuths(authorities);
            return response;
        }

        return new ResLoginDto();
    }
}
