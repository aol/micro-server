package app.custom.binder.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.module.ConfigurableModule;



@Rest
@Path("/test")
public class SimpleApp {

	public static void main(String[] args){
		new MicroserverApp(ConfigurableModule.builder().context("test-app")
								.resourceConfigManager(rc->rc.register(new CustomBinder())).build())
								.run();
	}
	@GET
	public String myEndPoint(){
		return "hello world!";
	}

	
}
