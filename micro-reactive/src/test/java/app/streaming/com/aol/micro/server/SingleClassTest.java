package app.streaming.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import cyclops.stream.ReactiveSeq;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.reactive.EventQueueManager;
import com.aol.micro.server.reactive.rest.ReactiveRequest;
import com.aol.micro.server.reactive.rest.ReactiveResponse;
import com.aol.micro.server.testing.RestAgent;

@Microserver
@Path("/single")
public class SingleClassTest implements RestResource {

    RestAgent rest = new RestAgent();

    @Autowired
    EventQueueManager<String> manager;
    MicroserverApp server;

    static String lastRecieved = null;

    @Before
    public void startServer() {
        lastRecieved = null;
        server = new MicroserverApp(
                                    SingleClassTest.class, () -> "simple-app");
        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @PostConstruct
    public void busManager() {
        manager.forEach("ping", in -> lastRecieved = in);
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        List<String> boos = new ReactiveRequest(
                                                1000, 1000)
                                                           .getJsonStream("http://localhost:8080/simple-app/single/infinite-boo",
                                                                          String.class)
                                                           .toList();

        assertThat(boos.size(), is(5));
        // System.out.println(rest.get("http://localhost:8080/simple-app/single/ping"));
        // assertThat(rest.get("http://localhost:8080/simple-app/single/ping"),
        // is("[1,2,3,4]"));

        // assertThat(lastRecieved, equalTo("input"));

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/example")
    public Response streamExample() {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(
                                                   new OutputStreamWriter(
                                                                          os));
                for (int i = 0; i < 100_000_000; i++) {
                    writer.write("test");

                    writer.flush();
                    /**  try {
                        Thread.sleep(100l);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }**/
                }
            }
        };
        return Response.ok(stream)
                       .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/out")
    public Response output() {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < 100_000; i++) {
            b.append("test");

        }

        return Response.ok(b.toString())
                       .build();
    }

    @GET
    @Produces("application/json")
    @Path("/infinite-boo")
    public Response boo() {
        manager.push("ping", "input");
        Response response = ReactiveResponse.publishAsJson(ReactiveSeq.generate(() -> "boo!")
                                                                      .limit(5));

        System.out.println("created response");
        return response;
    }

    @GET
    @Produces("application/json")
    @Path("/ping")
    public Response ping() {
        manager.push("ping", "input");
        Response response = ReactiveResponse.publishAsJson(ReactiveSeq.of(1, 2, 3, 4)
                                                                      .limit(5));
        System.out.println("created response");
        return response;
    }

}