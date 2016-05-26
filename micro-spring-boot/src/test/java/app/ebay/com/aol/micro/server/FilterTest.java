package app.ebay.com.aol.micro.server;

import static org.junit.Assert.assertFalse;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.spring.boot.MicroSpringBoot;
import com.aol.micro.server.testing.RestAgent;

@Microserver
@MicroSpringBoot
public class FilterTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp( FilterTest.class, ()-> "simple-app");
		

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target("http://localhost:8080/simple-app/single/ping");

		Builder request = resource.request();
		request.accept(MediaType.TEXT_PLAIN);
		assertFalse(request.get().getHeaders().containsKey("Access-Control-Allow-Origin"));
		
		
	
	}

	
	
	
}