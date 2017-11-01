package app.boot.filter.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.RestResource;

@Component
@Path("/status")
public class FilterStatusResource implements RestResource {


	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}
	@GET
	@Produces("text/plain")
	@Path("/ping2")
	public String ping2() {
		return "ok";
	}

}