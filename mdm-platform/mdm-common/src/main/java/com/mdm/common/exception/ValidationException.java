package com.mdm.common.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final int code;

    public ValidationException(String message) {
        super(message);
        this.code = ErrorCode.VALIDATION_FAILED.getCode();
    }

    public ValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
