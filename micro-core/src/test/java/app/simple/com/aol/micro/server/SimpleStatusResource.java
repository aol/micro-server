package app.simple.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.aol.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class SimpleStatusResource  {

	
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		
		return "ok";
	}

	
}