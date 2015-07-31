package app.async.pipes.com.aol.micro.server;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;


@Path("/status")
@Component
public class PipesStatusResource implements RestResource {

	volatile int next=0;
	
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		this.enqueue("test","ping : " + next++);
		return "ok";
	}

	   
}