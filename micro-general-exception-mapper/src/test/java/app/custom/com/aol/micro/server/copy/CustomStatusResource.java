package app.custom.com.aol.micro.server.copy;

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
public class CustomStatusResource {

	@GET
	@Path("/ping")
	public String ping() {
		throw ExceptionSoftener.throwSoftenedException( new MyException());
		
	}

	
}