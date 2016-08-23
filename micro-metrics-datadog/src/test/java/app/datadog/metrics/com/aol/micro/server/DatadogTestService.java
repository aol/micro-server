package app.datadog.metrics.com.aol.micro.server;

import com.codahale.metrics.annotation.ExceptionMetered;
import org.springframework.stereotype.Component;
import sun.plugin.dom.exception.InvalidStateException;

@Component
public class DatadogTestService {

    //@ExceptionMetered(name = "datadog-plugin", cause = InvalidStateException.class)
    public void someMethod() {
        throw new InvalidStateException("Error");
    }
}
