package app.properties.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties = { "override", "oops" }, propertiesName = "application.properties")
@Path("/single")
public class PropertiesClassTest implements RestResource {

    RestAgent rest = new RestAgent();

    MicroserverApp server;
    @Value("${override}")
    String overrideValue;

    @Before
    public void startServer() {

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

        assertThat(rest.get("http://localhost:8080/simple-app/single/ping"), is("boo!"));

    }

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {
        return overrideValue;
    }

}