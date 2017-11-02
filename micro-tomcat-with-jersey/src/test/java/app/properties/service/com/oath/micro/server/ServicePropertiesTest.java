package app.properties.service.com.oath.micro.server;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;

@Rest
@Path("/single")
@Microserver(serviceTypePropertiesName="myservice.properties")
public class ServicePropertiesTest {

	RestAgent rest = new RestAgent();
	
	@Value("${type.property}")
	private String type;
	MicroserverApp server;
	@Before
	public void startServer() throws InterruptedException{
		
		server = new MicroserverApp(()-> "minimal-app");
		Thread.sleep(1000);
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/minimal-app/single/ping"),is("ok"));
		
		
	
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		assertThat(type,equalTo("set"));
		return "ok";
	}
	
	
}