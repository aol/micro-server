package app.datadog.metrics.com.aol.micro.server;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.datadog.metrics.DatadogMetricsConfigurer;
import com.aol.micro.server.testing.RestAgent;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DatadogMetricsRunnerTest {
    RestAgent rest = new RestAgent();
    DatadogTestMain server;

    @Before
    public void startServer() {
        server.start();
    }
    @After
    public void stopServer(){
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() {

        try {
            assertThat(rest.get("http://localhost:8080/datadog-app/metrics/ping"), is("ok"));
        } catch (Exception e) {

        }

        try {
            Thread.sleep(5000);
        } catch (Exception e) {}

    }
}
