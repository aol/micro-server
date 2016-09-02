package app.registry.config.com.aol.micro.server;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.cyclops.data.collections.extensions.standard.MapXs;
import com.aol.micro.server.HealthStatus;
import com.aol.micro.server.StatsSupplier;
import com.aol.micro.server.auto.discovery.Rest;

@Rest
@Path("/health")
public class HealthCheckerResource implements HealthStatus, StatsSupplier {

    private volatile boolean ok = true;
    private volatile Map<String, Map<String, String>> stats = null;

    @Override
    public boolean isOk() {
        return ok;
    }

    @GET
    @Path("/error")
    public String error() {
        ok = false;
        return "error set";
    }

    @GET
    @Path("/stats")
    public String stats() {
        stats = MapXs.of("hello", MapXs.of("world", "boo!"));
        return "stats set";
    }

    @Override
    public Map<String, Map<String, String>> get() {
        return stats;
    }

}
