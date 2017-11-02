package app.multi.events.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.events.RequestEvents;
import com.google.common.eventbus.EventBus;

@Component
@Path("/status")
public class MultiEventStatusResource implements RestResource {

    private final EventBus bus;

    @Autowired
    public MultiEventStatusResource(EventBus bus) {
        this.bus = bus;
    }

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {
        RequestEvents.start("get", 1l, bus, "typeA", "custom");
        try {
            return "ok";
        } finally {
            RequestEvents.finish("get", 1l, bus, "typeA", "custom");
        }
    }

    @GET
    @Produces("text/plain")
    @Path("/ping-custom")
    public String pingCustom() {
        RequestEvents.start("get", 1l, bus, "typeA", "custom");
        try {
            return "ok";
        } finally {
            RequestEvents.finish("get", 1l, bus, "typeA", "custom");
        }
    }

}