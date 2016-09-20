package app.health.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.HealthStatusChecker;
import com.aol.micro.server.auto.discovery.Rest;

@Rest
@Path("/test")
public class HealthTestResource {

    @Autowired
    HealthStatusChecker status;

    @GET
    @Produces("text/plain")
    public String health() {
        return "" + status.isOk();

    }
}
