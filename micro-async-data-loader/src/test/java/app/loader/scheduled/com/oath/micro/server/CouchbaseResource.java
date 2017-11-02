package app.loader.scheduled.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import cyclops.collections.immutable.LinkedListX;
import cyclops.control.Maybe;
import org.springframework.beans.factory.annotation.Autowired;



import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.distributed.DistributedMap;
import com.oath.micro.server.events.SystemData;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@Path("/couchbase")
@Rest
public class CouchbaseResource {

    private final DistributedMap client;
    private volatile LinkedListX<SystemData> dataLoads = LinkedListX.empty();

    @Autowired
    public CouchbaseResource(DistributedMap client, EventBus bus) {
        this.client = client;
        bus.register(this);
    }

    @Subscribe
    public synchronized void events(SystemData event) {
        dataLoads = dataLoads.plus(event);

    }

    @GET
    @Path("/loading-events")
    @Produces("application/json")
    public synchronized LinkedListX<SystemData> loadingEvents() {
        return dataLoads;
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
