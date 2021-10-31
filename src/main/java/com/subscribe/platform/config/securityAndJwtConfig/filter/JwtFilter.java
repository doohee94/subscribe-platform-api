package com.subscribe.platform.config.securityAndJwtConfig.filter;

import com.subscribe.platform.config.error.exception.AccessDeniedException;
import com.subscribe.platform.config.securityAndJwtConfig.SecurityConfig;
import com.subscribe.platform.config.securityAndJwtConfig.service.CustomUserDetailService;
import com.subscribe.platform.config.securityAndJwtConfig.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException {
        String autorizationHeader = request.getHeader(SecurityConfig.AUTHENTICATION_HEADER_NAME);

        String token = null;
        String email = null;

        if(autorizationHeader != null){
            token = autorizationHeader;
            if(jwtUtil.isTokenExpired(token)){   // 토큰 만료 에러 잡기 위해
                email = jwtUtil.extractUsername(token);
            }

        }

        /**
         * 로그인
         */
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = service.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // 꼭 써줘야 다른 필터가 존재하면 필터 진행하고 없으면 서블릿 실행
        filterChain.doFilter(request, response);
    }
}
