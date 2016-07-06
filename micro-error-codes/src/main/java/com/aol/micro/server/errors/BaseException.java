package com.aol.micro.server.errors;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class BaseException extends RuntimeException {

	ErrorCode errorCode;
	Object[] data;

	public BaseException(final Throwable cause) {
		super(cause);
	}

	public BaseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BaseException(final String message) {
		super(message);
	}

	public BaseException(final ErrorCode errorCode, final Throwable cause) {
		super(errorCode.renderSafe(), cause);
		this.errorCode = errorCode;
	}

	public BaseException(final ErrorCode errorCode, final Object... data) {
		super(errorCode.renderSafe());
		this.errorCode = errorCode;
		this.data = data;
	}

	public BaseException(final ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		if (errorCode != null) {
			return MessageFormat.format(errorCode.renderAsJavaMessageFormat(), data);

		}
		return super.getMessage();
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
