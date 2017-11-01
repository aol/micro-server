package app.servlet.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.RestResource;

@Component
@Path("/servlet")
public class ServletStatusResource implements RestResource {

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}

}