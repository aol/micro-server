package app.datadog.metrics.com.aol.micro.server;

import com.oath.micro.server.testing.RestAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
