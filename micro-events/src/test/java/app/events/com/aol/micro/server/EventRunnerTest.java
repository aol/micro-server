package app.events.com.aol.micro.server;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

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
        assertThat(rest.getJson("http://localhost:8080/event-app/manifest"), containsString("Manifest-Version"));

        System.out.println(rest.getJson("http://localhost:8080/event-app/manifest"));

    }

}
