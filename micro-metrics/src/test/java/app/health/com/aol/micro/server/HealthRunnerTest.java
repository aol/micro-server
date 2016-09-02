package app.health.com.aol.micro.server;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.spring.metrics.CodahaleMetricsConfigurer;
import com.aol.micro.server.testing.RestAgent;

import app.metrics.com.aol.micro.server.TestReporter;

@Configuration
@ComponentScan(basePackages = { "app.metrics.com.aol.micro.server" })
public class HealthRunnerTest {

    RestAgent rest = new RestAgent();

    MicroserverApp server;

    @Before
    public void startServer() {
        CodahaleMetricsConfigurer.setInit(metricRegistry -> TestReporter.forRegistry(metricRegistry)
                                                                        .build()
                                                                        .start(10, TimeUnit.MILLISECONDS));

        server = new MicroserverApp(
                                    HealthRunnerTest.class, () -> "simple-app");
        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException, IOException {

        assertThat(rest.getJson("http://localhost:8080/simple-app/health"),
                   is("{\"myHealthCheck\":{\"healthy\":true}}"));
        assertThat(rest.get("http://localhost:8080/simple-app/test"), is("true"));

    }

}
