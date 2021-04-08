package com.subscribe.platform.user.exception;

import com.subscribe.platform.global.error.exception.BusinessException;
import com.subscribe.platform.global.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
