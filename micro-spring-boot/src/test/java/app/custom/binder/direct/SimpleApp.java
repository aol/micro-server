package app.custom.binder.direct;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.oath.micro.server.auto.discovery.Rest;


@Rest
@Path("/test")
public class SimpleApp {

	
	@GET
	public String myEndPoint(){
		return "hello world!";
	}

	
}
