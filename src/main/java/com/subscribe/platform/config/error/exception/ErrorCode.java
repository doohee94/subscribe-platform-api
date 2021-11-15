package com.subscribe.platform.config.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "ENTITY_NOT_FOUND", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "UNEXPECTED ERROR", "Server Error"),
    INVALID_TYPE_VALUE(400, "INVALID_TYPE_VALUE", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "HANDLE_ACCESS_DENIED", "Access is Denied"),

    //auth
    INVALID_TOKEN(401, "UNAUTHORIZED", "invalid token"),
    JWT_EXPIRED(401,"UNAUTHORIZED", "JWT Token expired"),
    LOGIN_FAIL(401, "UNAUTHORIZED", "login fail"),

    //date
    INVALID_DATE_TYPE(400, "INVALID_DATE_TYPE","잘못된 날짜 형식"),
    ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}
