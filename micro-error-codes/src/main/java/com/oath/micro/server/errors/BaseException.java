package com.oath.micro.server.errors;

import lombok.Getter;

@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public BaseException(final ErrorCode errorCode, final Throwable cause) {
        super(
              errorCode.toString(), cause);
        this.errorCode = errorCode;

    }

    public BaseException(final ErrorCode errorCode) {
        super(
              errorCode.toString());
        this.errorCode = errorCode;

    }

}
