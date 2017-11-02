package app.jackson.custom.com.oath.micro.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.oath.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class CustomStatusResource {

	@POST
	@Path("/ping")
	public String ping(MyEntity entity) {
		return "ok";
		
	}

	
}