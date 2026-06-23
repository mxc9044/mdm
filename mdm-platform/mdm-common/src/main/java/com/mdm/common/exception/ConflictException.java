package com.mdm.common.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    private final int code;

    public ConflictException(String message) {
        super(message);
        this.code = ErrorCode.CONFLICT.getCode();
    }

    public ConflictException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
