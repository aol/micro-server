package app.health.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.oath.micro.server.HealthStatusChecker;
import com.oath.micro.server.auto.discovery.Rest;

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
