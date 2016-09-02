package app.datadog.metrics.com.aol.micro.server;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("/metrics")
@Rest
public class DatadogMetricsStatusResource implements RestResource {

    private final DatadogTestService service;

    @Autowired
    public DatadogMetricsStatusResource(DatadogTestService service) {
        this.service = service;
    }

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {
        try {
            service.someMethod();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "ok";
    }

}
