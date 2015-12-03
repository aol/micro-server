package app.metrics.boot.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import com.aol.micro.server.auto.discovery.RestResource;

@Component
@Path("/metrics")
public class MetricsStatusResource implements RestResource {

	@Autowired
	TimedResource timed;
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		timed.times();
		return "ok";
	}

}