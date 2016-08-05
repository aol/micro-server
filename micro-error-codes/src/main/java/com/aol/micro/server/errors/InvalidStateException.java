package com.aol.micro.server.errors;

import com.aol.micro.server.health.ErrorEvent;

public class InvalidStateException extends BaseException {

    private static final long serialVersionUID = 1L;

    public InvalidStateException(final FormattedErrorCode errorCode) {
        super(
              errorCode.getCode());
        new ErrorBus().post(new ErrorEvent(
                                           this));
    }

    public InvalidStateException(final FormattedErrorCode errorCode, final Throwable cause) {
        super(
              errorCode.getCode(), cause);
        new ErrorBus().post(new ErrorEvent(
                                           this));
    }

}
