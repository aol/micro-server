package com.aol.micro.server.errors;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import lombok.Getter;

@Getter
public class ErrorCode {

    private final int errorId;
    private final String message;
    private final Severity severity;

    private final ErrorBus errorBus = new ErrorBus();

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

    private ErrorCode(final int errorId, final String message, final Severity severity) {

        this.errorId = errorId;
        this.message = message;
        this.severity = severity;

    }

    public String renderAsJavaMessageFormat() {
        final StringBuilder result = new StringBuilder();
        int pos = 0;

        String renderSafeStr = renderSafe();

        if (!renderSafeStr.contains("{}")) {
            result.append(renderSafeStr);
        } else {
            List<String> strList = Lists.newLinkedList(Splitter.on("{}")
                                                               .omitEmptyStrings()
                                                               .split(renderSafeStr));
            int count = 0;

            for (final String next : strList) {
                result.append(next);
                count++;

                if (renderSafeStr.endsWith("{}")) {
                    result.append("{")
                          .append(pos++)
                          .append("}");
                } else if (count < strList.size()) {
                    result.append("{")
                          .append(pos++)
                          .append("}");
                }
            }
        }
        return result.toString();
    }

    public String renderSafe() {
        return "Error ID (" + errorId + ") :" + " - " + message;
    }
}
