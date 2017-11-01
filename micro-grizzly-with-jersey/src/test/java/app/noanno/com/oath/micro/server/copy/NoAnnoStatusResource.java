package app.noanno.com.oath.micro.server.copy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.oath.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class NoAnnoStatusResource  {

	
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		
		return "ok";
	}

	
}