package app.single.main.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.Ignore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.oath.micro.server.MicroserverApp;

@Configuration
@ComponentScan(basePackages = { "app.simple.com.aol.micro.server" })
@Component
@Path("/status") @Ignore
public class SingleClassApp {

	public static void main(String[] args){
		new MicroserverApp( SingleClassApp.class, ()-> "simple-app");
	}
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}
	
}
