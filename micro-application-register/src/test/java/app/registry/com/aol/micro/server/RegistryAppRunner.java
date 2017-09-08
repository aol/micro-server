package app.registry.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.application.registry.RegisterEntry;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.client.nio.AsyncRestClient;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties = { "service.registry.url", "http://localhost:8080/registry-app", "target.endpoint",
        "configured-target" })
public class RegistryAppRunner {

    RestAgent rest = new RestAgent();
    private final AsyncRestClient restAsync = new AsyncRestClient(
                                                                  100, 2000);
    MicroserverApp server;

    @Before
    public void startServer() {

        server = new MicroserverApp(
                                    () -> "registry-app");
        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        SimpleDateFormat f = new SimpleDateFormat(
                                                  "EEE");
        String date = f.format(new Date());
        Thread.sleep(1000);

        assertThat(rest.post("http://localhost:8080/registry-app/service-registry/schedule"),
                   is("{\"status\":\"success\"}"));
        Thread.sleep(1000);
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("[{\"port\":8080,"));
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("externalPort\":8080"));

        sendPing(new RegisterEntry(
                                   8081, "use-ip", "hello", "world", new Date(), "my-target", 8082));
        Thread.sleep(1000);
        System.out.println(rest.getJson("http://localhost:8080/registry-app/service-registry/list"));
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("{\"port\":8081,"));

        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("\"target\":\"my-target\""));
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("\"target\":\"configured-target\""));
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   not(containsString("\"hostname\":\"test-host\"")));
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("\"formattedDate\""));
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("\"manifest\""));
        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),
                   containsString("Manifest-Version"));

        assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"), containsString(date));

    }

    private void sendPing(RegisterEntry entry) {

        try {

            restAsync.post("http://localhost:8080/registry-app/service-registry/register",
                           JacksonUtil.serializeToJson(entry))
                     .join();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        RegistryAppRunner appRunner = new RegistryAppRunner();
        appRunner.startServer();
    }

}
