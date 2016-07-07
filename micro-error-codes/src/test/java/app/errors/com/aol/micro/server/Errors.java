package app.errors.com.aol.micro.server;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.errors.Severity;

public interface Errors {
    public final static ErrorCode QUERY_FAILURE = ErrorCode.error(1, "something {0} bad happened", Severity.CRITICAL);
    public final static ErrorCode SYSTEM_FAILURE = ErrorCode.error(2, "something {0} really bad", Severity.FATAL);
}
