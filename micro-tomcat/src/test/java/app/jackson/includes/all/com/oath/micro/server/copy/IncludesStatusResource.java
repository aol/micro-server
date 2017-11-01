package app.jackson.includes.all.com.oath.micro.server.copy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.oath.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class IncludesStatusResource {

	@GET
	@Path("/ping")
	@Produces("application/json")
	public MyEntity ping() {
		return new MyEntity();
		
	}

	
}