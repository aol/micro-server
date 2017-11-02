package app.error.com.oath.micro.server;

import java.io.EOFException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


import com.oath.cyclops.util.ExceptionSoftener;
import com.oath.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class ErrorStatusResource {

	@GET
	@Path("/ping")
	public String ping() {
		throw ExceptionSoftener.throwSoftenedException( new EOFException("hello world"));
		
	}

	
}