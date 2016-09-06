package app.properties.override.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties = { "simple-app.port", "8080" }, propertiesName = "application.properties")
@Path("/single")
public class PropertiesClassTest implements RestResource {

    RestAgent rest = new RestAgent();

    MicroserverApp server;

    @Before
    public void startServer() {

        System.setProperty("simple-app.port", "8081");
        server = new MicroserverApp(
                                    PropertiesClassTest.class, () -> "simple-app");
        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        assertThat(rest.get("http://localhost:8081/simple-app/single/ping"), is("boo!"));

    }

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {
        return "boo!";
    }

}