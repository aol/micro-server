package app.datadog.metrics.com.aol.micro.server;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;

@Microserver(basePackages = { "app.datadog.metrics.com.oath.micro.server", "com.oath.micro.server.datadog.metrics", "com.oath.micro.server.event.metrics"})
public class DatadogTestMain {

    public static final String CONTEXT = "datadog-app";

    public static void start() {
        new MicroserverApp(() -> CONTEXT).start();
    }

    public static void stop() {
        new MicroserverApp(() -> CONTEXT).stop();
    }
}
