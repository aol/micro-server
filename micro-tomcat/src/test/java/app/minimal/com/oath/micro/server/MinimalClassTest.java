package app.minimal.com.oath.micro.server;

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
import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.testing.RestAgent;

@Rest
@Path("/single")
public class MinimalClassTest {

    RestAgent rest = new RestAgent();

    MicroserverApp server;

    @Before
    public void startServer() {

        server = new MicroserverApp(
                                    () -> "minimal-app");
        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        Thread.sleep(500l);
        assertThat(rest.get("http://localhost:8080/minimal-app/single/ping"), is("ok"));

    }

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {
        return "ok";
    }

}