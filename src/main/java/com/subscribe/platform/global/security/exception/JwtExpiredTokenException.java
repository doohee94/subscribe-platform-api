package com.subscribe.platform.global.security.exception;

import com.subscribe.platform.global.error.exception.BusinessException;
import com.subscribe.platform.global.error.exception.ErrorCode;

/**
 * 유효하지 않은 JWT 토큰 예외 클래스
 */
public class JwtExpiredTokenException extends BusinessException {
    private static final long serialVersionUID = -5959543783324224864L;

    private String token;


    public JwtExpiredTokenException(String token, String msg, Throwable t) {
        super(msg, ErrorCode.ENTITY_NOT_FOUND);
        this.token = token;
    }
}
