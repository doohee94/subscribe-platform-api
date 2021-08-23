package com.subscribe.platform.user.controller;

import com.subscribe.platform.config.securityAndJwtConfig.util.JwtUtil;
import com.subscribe.platform.user.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDto loginDto) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        Map<String, Object> response = new HashMap();
        if(authenticate.isAuthenticated()){
            response.put("token", jwtUtil.generateToken(authenticate.getName(), (List<GrantedAuthority>) authenticate.getAuthorities()));
            response.put("auths", authenticate.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList()));
            return response;
        }

        return response;
    }
}
