package app.custom.com.aol.micro.server.copy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.cyclops.util.ExceptionSoftener;
import com.aol.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class CustomStatusResource {

	@GET
	@Path("/ping")
	public String ping() {
		throw ExceptionSoftener.throwSoftenedException( new MyException());
		
	}

	
}