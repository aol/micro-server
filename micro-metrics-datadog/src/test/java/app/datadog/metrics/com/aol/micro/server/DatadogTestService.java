package app.datadog.metrics.com.aol.micro.server;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.errors.InvalidStateException;
import org.springframework.stereotype.Component;

@Component
public class DatadogTestService {

    public void someMethod() {
        throw new InvalidStateException(ErrorCode.high(100,"Some Error Message").format());
    }
}
