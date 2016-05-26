package app.custom.binder.direct;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.module.ConfigurableModule;



@Rest
@Path("/test")
public class SimpleApp {

	
	@GET
	public String myEndPoint(){
		return "hello world!";
	}

	
}
