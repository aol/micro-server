package app.metrics.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.metrics.com.aol.micro.server.TimedResource;

import com.aol.micro.server.auto.discovery.RestResource;

@Component
@Path("/status")
public class ServletStatusResource implements RestResource {

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