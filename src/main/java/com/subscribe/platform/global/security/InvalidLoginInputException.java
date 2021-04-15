package com.subscribe.platform.global.security;

import com.subscribe.platform.global.error.exception.BusinessException;
import com.subscribe.platform.global.error.exception.ErrorCode;

public class InvalidLoginInputException extends BusinessException {


    public InvalidLoginInputException(String log) {
        super(log,ErrorCode.INVALID_INPUT_VALUE);
    }
}
