package app.datadog.metrics.com.aol.micro.server;

import com.codahale.metrics.annotation.ExceptionMetered;
import org.springframework.stereotype.Component;

@Component
public class DatadogTestService {

    @ExceptionMetered(name = "datadog-plugin", cause = RuntimeException.class)
    public void someMethod() {
        throw new RuntimeException("Error");
    }
}
