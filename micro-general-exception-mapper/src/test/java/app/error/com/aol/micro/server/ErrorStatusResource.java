package app.error.com.aol.micro.server;

import java.io.EOFException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


import com.aol.cyclops2.util.ExceptionSoftener;
import com.aol.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class ErrorStatusResource {

	@GET
	@Path("/ping")
	public String ping() {
		throw ExceptionSoftener.throwSoftenedException( new EOFException("hello world"));
		
	}

	
}