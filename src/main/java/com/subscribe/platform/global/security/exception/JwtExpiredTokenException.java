package com.subscribe.platform.global.security.exception;

import com.subscribe.platform.global.error.exception.BusinessException;
import com.subscribe.platform.global.error.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;

public class JwtExpiredTokenException extends BusinessException {

    public JwtExpiredTokenException() {
        super(ErrorCode.JWT_EXPIRED);
    }

    public JwtExpiredTokenException(String claimsJws, String jwt_token_expired, ExpiredJwtException expiredEx) {
        super(claimsJws+ jwt_token_expired+ expiredEx,ErrorCode.JWT_EXPIRED);
    }
}
