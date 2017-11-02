package app.multi.events.com.oath.micro.server;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;

@Microserver
public class MulitEventRunnerTest {

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
        assertThat(rest.getJson("http://localhost:8080/event-app/active/all-requests"), containsString("startedAt"));
        assertThat(rest.getJson("http://localhost:8080/event-app/manifest"), containsString("Manifest"));
        assertThat(rest.getJson("http://localhost:8080/event-app/manifest"), containsString("Manifest-Version"));

        System.out.println(rest.getJson("http://localhost:8080/event-app/manifest"));

    }

    @Test
    public void runAppAndBasicTestCustom() throws InterruptedException, ExecutionException {

        assertThat(rest.get("http://localhost:8080/event-app/status/ping-custom"), is("ok"));

        assertThat(rest.getJson("http://localhost:8080/event-app/active/jobs?type=custom"),
                   containsString("startedAt"));
        assertThat(rest.getJson("http://localhost:8080/event-app/active/jobs?type=typeA"), containsString("startedAt"));
        assertThat(rest.getJson("http://localhost:8080/event-app/active/all-requests"), containsString("startedAt"));
        assertThat(rest.getJson("http://localhost:8080/event-app/manifest"), containsString("Manifest"));
        assertThat(rest.getJson("http://localhost:8080/event-app/manifest"), containsString("Manifest-Version"));

        System.out.println(rest.getJson("http://localhost:8080/event-app/manifest"));

    }

}
