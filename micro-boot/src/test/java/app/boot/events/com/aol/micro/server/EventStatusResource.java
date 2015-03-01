package app.boot.events.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.events.RequestEvents;
import com.google.common.eventbus.EventBus;

@Component
@Path("/status")
public class EventStatusResource implements RestResource {


	
	
	private final EventBus bus;
	
	@Autowired
	public EventStatusResource(EventBus bus ){
		this.bus = bus;
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		bus.post(RequestEvents.start("get", 1l));
		try{
			return "ok";
		}finally{
			bus.post(RequestEvents.finish("get",1l));
		}
	}

}