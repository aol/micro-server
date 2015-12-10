package app.custom.binder.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.module.ConfigurableModule;



@Rest
@Path("/test")
public class SimpleApp {

	public static void main(String[] args){
		new MicroserverApp(ConfigurableModule.builder().context("test-app")
					.build().<ResourceConfig>withResourceConfigManager(rc->rc.getJaxRsConfig().register(new CustomBinder())))
								.run();
	}
	@GET
	public String myEndPoint(){
		return "hello world!";
	}

	
}
