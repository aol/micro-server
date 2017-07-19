package app.cleaner.scheduled.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.aol.micro.server.distributed.DistributedCache;
import cyclops.collections.immutable.LinkedListX;
import cyclops.control.Maybe;
import org.springframework.beans.factory.annotation.Autowired;


import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.distributed.DistributedMap;
import com.aol.micro.server.events.SystemData;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@Path("/couchbase")
@Rest
public class CouchbaseResource {

    private final DistributedCache client;

    private volatile LinkedListX<SystemData> dataCleans = LinkedListX.empty();

    @Autowired
    public CouchbaseResource(DistributedCache client, EventBus bus) {
        this.client = client;
        bus.register(this);
    }

    @Subscribe
    public synchronized void events(SystemData event) {
        dataCleans = dataCleans.plus(event);
    }

    @GET
    @Path("/cleaning-events")
    @Produces("application/json")
    public synchronized LinkedListX<SystemData> cleaningEvents() {
        return dataCleans;
    }

    @GET
    @Path("/maybe")
    @Produces("application/json")
    public Maybe<String> maybe() {
        return Maybe.just("hello-world");
    }

    @GET
    @Path("/get")
    public String bucket() {
        return client.get("hello")
                     .toString();
    }

    @GET
    @Path("/put")
    public String put() {
        client.put("hello", "world");
        return "added";
    }
}
