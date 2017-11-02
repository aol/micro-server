package app.errors.com.oath.micro.server;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.errors.InvalidStateException;
import com.oath.micro.server.health.HealthStatus;
import com.oath.micro.server.testing.RestAgent;

@Microserver
@Path("/single")
public class SingleClassTest implements RestResource {

    RestAgent rest = new RestAgent();

    MicroserverApp server;

    @Before
    public void startServer() {

        server = new MicroserverApp(
                                    SingleClassTest.class, () -> "simple-app");
        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        rest.get("http://localhost:8080/simple-app/single/error");

        assertThat(rest.get("http://localhost:8080/simple-app/system-errors/status"),
                   is(HealthStatus.State.Errors.name()));
        assertThat(rest.getJson("http://localhost:8080/simple-app/system-errors/errors"),
                   containsString("formatted-date"));

        rest.get("http://localhost:8080/simple-app/single/fatal");

        System.out.println(rest.getJson("http://localhost:8080/simple-app/system-errors/errors"));
        assertThat(rest.get("http://localhost:8080/simple-app/system-errors/status"),
                   is(HealthStatus.State.Fatal.name()));

    }

    @GET
    @Produces("text/plain")
    @Path("/error")
    public String error() {
        try {
            throw new InvalidStateException(
                                            Errors.QUERY_FAILURE.format("*eek*"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GET
    @Produces("text/plain")
    @Path("/fatal")
    public String fatal() {
        try {
            throw new InvalidStateException(
                                            Errors.SYSTEM_FAILURE.format("*eek*"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unrecoverable fatal error";
    }

}