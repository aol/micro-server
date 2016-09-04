package com.aol.micro.server.errors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class FormattedErrorCode {
    private final ErrorCode code;
}
