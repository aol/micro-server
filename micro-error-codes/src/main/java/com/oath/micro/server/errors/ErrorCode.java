package com.oath.micro.server.errors;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ErrorCode {

    private final int errorId;
    private final String message;
    private final Severity severity;

    public static ErrorCode error(final int errorId, final String message, final Severity severity) {
        return new ErrorCode(
                             errorId, message, severity);
    }

    public static ErrorCode low(final int errorId, final String message) {
        return new ErrorCode(
                             errorId, message, Severity.LOW);
    }

    public static ErrorCode medium(final int errorId, final String message) {
        return new ErrorCode(
                             errorId, message, Severity.MEDIUM);
    }

    public static ErrorCode high(final int errorId, final String message) {
        return new ErrorCode(
                             errorId, message, Severity.HIGH);
    }

    public static ErrorCode critical(final int errorId, final String message) {
        return new ErrorCode(
                             errorId, message, Severity.CRITICAL);
    }

    private ErrorCode(@JsonProperty("errorId") final int errorId, @JsonProperty("message") final String message,
            @JsonProperty("severity") final Severity severity) {

        this.errorId = errorId;
        this.message = message;
        this.severity = severity;

    }

    public FormattedErrorCode format(Object... data) {
        return new FormattedErrorCode(
                                      new ErrorCode(
                                                    errorId, formatStr(data), severity));
    }

    public String formatStr(Object... data) {
        return MessageFormat.format(message, data);
    }

    @Override
    public String toString() {
        return "Error ID (" + errorId + ") :" + " - " + message;
    }
}
