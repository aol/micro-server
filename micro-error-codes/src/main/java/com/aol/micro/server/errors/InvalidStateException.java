package com.aol.micro.server.errors;

import com.aol.micro.server.health.ErrorEvent;

public class InvalidStateException extends BaseException {

    private static final long serialVersionUID = 1L;

    public InvalidStateException(final ErrorCode errorCode) {
        super(
              errorCode);
        new ErrorBus().post(new ErrorEvent(
                                           this));
    }

    public InvalidStateException(final ErrorCode errorCode, final Throwable cause) {
        super(
              errorCode, cause);
        new ErrorBus().post(new ErrorEvent(
                                           this));
    }

}
