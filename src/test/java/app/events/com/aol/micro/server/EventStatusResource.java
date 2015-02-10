package app.events.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.events.RequestsBeingExecuted;
import com.google.common.eventbus.EventBus;

@Component
@Path("/status")
public class EventStatusResource implements RestResource {


	
	
	EventBus bus;
	
	@Autowired
	public EventStatusResource(EventBus bus ){
		this.bus = bus;
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		bus.post(RequestsBeingExecuted.start("get", 1l));
		try{
			return "ok";
		}finally{
			bus.post(RequestsBeingExecuted.finish("get",1l));
		}
	}

}