package app.errors.com.oath.micro.server;

import com.oath.micro.server.errors.ErrorCode;
import com.oath.micro.server.errors.Severity;

public interface Errors {
    public final static ErrorCode QUERY_FAILURE = ErrorCode.error(1, "something {0} bad happened", Severity.CRITICAL);
    public final static ErrorCode SYSTEM_FAILURE = ErrorCode.error(2, "something {0} really bad", Severity.FATAL);
}
