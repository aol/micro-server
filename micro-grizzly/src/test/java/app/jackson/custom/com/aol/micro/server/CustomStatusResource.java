package app.jackson.custom.com.aol.micro.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.aol.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class CustomStatusResource {

	@POST
	@Path("/ping")
	public String ping(MyEntity entity) {
		return "ok";
		
	}

	
}