package app.error.com.aol.micro.server;

import java.io.EOFException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.aol.cyclops.invokedynamic.ExceptionSoftener;
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