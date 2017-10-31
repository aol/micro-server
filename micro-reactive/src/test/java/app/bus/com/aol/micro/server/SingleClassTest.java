package app.bus.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

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

        assertThat(rest.get("http://localhost:8080/simple-app/single/ping"), is("ok"));
        Thread.sleep(500);


    }

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {

        return "ok";
    }

}