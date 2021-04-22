package com.subscribe.platform.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subscribe.platform.global.security.jwt.JwtUtil;
import com.subscribe.platform.global.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String API_ROOT_URL = "/api/**";

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final AuthenticationSuccessHandler successHandler;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/webjars/**", "/swagger/**",
                        "/swagger-ui.html", "/swagger-resources", "/swagger-resources/**",
                        "/api/v2/api-docs", "/v2/api-docs",
                        "/configuration/security", "/configuration/ui"
                )  //SWAGGER 접근 허용
                .antMatchers("/css/**", "/js/**", "/img/**")
        ;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.csrf().disable()
                .headers().frameOptions().disable();

        security.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        security.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/user/test/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
        ;

    }

    /**
     * Provider 등록
     *
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }


    /**
     * 비동기 로그인 처리를 위한 필터 생성하고, authenticationManager를 등록해줌. 후에 반환.
     * @return
     * @throws Exception
     */
//        private AsyncLoginProcessingFilter buildAsyncLoginProcessingFilter() throws Exception {
//            AsyncLoginProcessingFilter filter = new AsyncLoginProcessingFilter(AUTHENTICATION_URL, objectMapper, successHandler);
//            filter.setAuthenticationManager(this.authenticationManager());
//            return filter;
//        }

    /**
     * 토큰 검즈을 위한 필터 생성하고, authenticationManager를 등록해줌. 후에 반환.
     *
     * @param pathsToSkip
     * @param pattern
     * @return
     * @throws Exception
     */
//    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
//        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
//        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher, failureHandler, jwtUtil);
//        filter.setAuthenticationManager(this.authenticationManager());
//        return filter;
//    }


}
