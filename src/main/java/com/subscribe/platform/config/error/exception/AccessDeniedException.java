package com.subscribe.platform.config.error.exception;

public class AccessDeniedException extends BusinessException{

    public AccessDeniedException(String message){
        super(message, ErrorCode.HANDLE_ACCESS_DENIED);
    }
}
