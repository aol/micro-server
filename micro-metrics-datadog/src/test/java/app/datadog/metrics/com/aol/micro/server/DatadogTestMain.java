package app.datadog.metrics.com.aol.micro.server;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;

@Microserver(basePackages = { "app.datadog.metrics.com.aol.micro.server", "com.aol.micro.server.datadog.metrics", "com.aol.micro.server.event.metrics"})
public class DatadogTestMain {

    public static final String CONTEXT = "datadog-app";

    public static void start() {
        new MicroserverApp(() -> CONTEXT).start();
    }

    public static void stop() {
        new MicroserverApp(() -> CONTEXT).stop();
    }
}
