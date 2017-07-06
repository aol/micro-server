package app.publisher.binder.direct;

import com.aol.micro.server.auto.discovery.Rest;
import cyclops.async.Future;
import cyclops.stream.ReactiveSeq;
import cyclops.stream.Spouts;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.Executors;
import java.util.stream.Stream;


@Rest
@Path("/test")
public class AsyncResource {

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("myEndPoint")
    public Future<String> myEndPoint() {
        return Future.of(() -> {
            sleep();
            return "hello world!";
        }, Executors.newFixedThreadPool(1));
    }

    @GET
    @Path("async2")
    public ReactiveSeq<String> async2() {
        return Spouts.reactive(Stream.of("hello"), Executors.newFixedThreadPool(1));
    }


}
