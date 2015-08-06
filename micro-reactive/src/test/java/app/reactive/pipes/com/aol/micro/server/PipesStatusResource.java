package app.reactive.pipes.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.reactive.Reactive;


@Path("/status")
@Rest
public class PipesStatusResource implements Reactive {

	volatile int next=0;
	
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		this.enqueue("test","ping : " + next++);
		return "ok";
	}

	   
}