package com.aol.micro.server.errors;

import com.aol.micro.server.health.ErrorEvent;

public class InvalidStateException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public InvalidStateException(final ErrorCode errorCode){
		super(errorCode);
		new ErrorBus().post(new ErrorEvent(this));
	}
	
	public InvalidStateException(final ErrorCode errorCode, final Throwable cause){
		super(errorCode, cause);
		new ErrorBus().post(new ErrorEvent(this));
	}
	
	public InvalidStateException(final ErrorCode errorCode,
			final Object... data) {
		super(errorCode,data);
		new ErrorBus().post(new ErrorEvent(this));
	}
	
	public InvalidStateException(final ErrorCode errorCode,
			final Throwable cause, final Object... data) {
		super(errorCode,cause, data);
		new ErrorBus().post(new ErrorEvent(this));
	}

	InvalidStateException(final Void noPost, final ErrorCode errorCode,
			final Object... data){
		super(errorCode,data);
		if(noPost!=null){
			new ErrorBus().post(new ErrorEvent(this));
		}
	}

	
}
