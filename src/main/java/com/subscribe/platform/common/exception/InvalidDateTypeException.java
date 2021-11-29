package com.subscribe.platform.common.exception;

import com.subscribe.platform.config.error.exception.BusinessException;
import com.subscribe.platform.config.error.exception.ErrorCode;

public class InvalidDateTypeException extends BusinessException {
	public InvalidDateTypeException() {
		super(ErrorCode.INVALID_DATE_TYPE);
	}
}
