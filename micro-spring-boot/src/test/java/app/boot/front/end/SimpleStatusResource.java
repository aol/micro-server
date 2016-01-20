package app.boot.front.end;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;

@Component
@Qualifier("simpleStatusResource")
@Path("/status")
public class SimpleStatusResource implements RestResource {

	
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		
		return "ok";
	}

	
}