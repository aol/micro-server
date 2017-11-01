package app.event.metrics.com.aol.micro.server;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.jackson.JacksonUtil;
import com.oath.micro.server.testing.RestAgent;

@Microserver
public class EventRunnerTest {

    RestAgent rest = new RestAgent();
    MicroserverApp server;

    @Before
    public void startServer() {

        server = new MicroserverApp(
                                    () -> "event-app");
        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        assertThat(rest.get("http://localhost:8080/event-app/status/ping"), is("ok"));

        assertThat(rest.getJson("http://localhost:8080/event-app/active/jobs"), containsString("startedAt"));
        assertThat(rest.getJson("http://localhost:8080/event-app/active/requests"), containsString("startedAt"));
        assertThat(rest.getJson("http://localhost:8080/event-app/manifest"), containsString("Manifest"));

        String json = rest.getJson("http://localhost:8080/event-app/status/counters");
        Map<String, Integer> map = JacksonUtil.convertFromJson(json, Map.class);

        assertThat(json, map.get("com.aol.micro.server.event.metrics.MetricsCatcher.jobs-completed-count"),
                   greaterThan(1));

        String json2 = rest.getJson("http://localhost:8080/event-app/status/meters");
        Map<String, Integer> map2 = JacksonUtil.convertFromJson(json2, Map.class);

        assertThat(json2, map2.get("com.aol.micro.server.event.metrics.MetricsCatcher.request-start-default-meter"),
                   greaterThan(0));

    }

}
