package app.event.metrics.com.oath.micro.server;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import cyclops.reactive.collections.mutable.MapX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.events.RequestEvents;
import com.codahale.metrics.MetricRegistry;
import com.google.common.eventbus.EventBus;

@Component
@Path("/status")
public class EventStatusResource implements RestResource {

    private final EventBus bus;
    private final MetricRegistry metrics;

    @Autowired
    public EventStatusResource(EventBus bus, MetricRegistry metrics) {
        this.bus = bus;
        this.metrics = metrics;
    }

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {
        bus.post(RequestEvents.start("get", 1l));
        try {
            return "ok";
        } finally {
            bus.post(RequestEvents.finish("get", 1l));
        }
    }

    @GET
    @Produces("application/json")
    @Path("/counters")
    public Map<String, Long> counters() {
        return (MapX) MapX.fromMap(metrics.getCounters())
                          .bimap(k -> k, v -> v.getCount());
    }

    @GET
    @Produces("application/json")
    @Path("/meters")
    public Map<String, Long> meters() {
        return (MapX) MapX.fromMap(metrics.getMeters())
                          .bimap(k -> k, v -> v.getCount());
    }

}
