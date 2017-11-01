package app.single.com.oath.micro.server;

import static org.junit.Assert.assertTrue;

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

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties={"cors.simple","true"})
@Path("/single")
public class SingleClassTest implements RestResource{

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp( SingleClassTest.class, ()-> "simple-app");
		server.start();

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
		assertTrue(request.get().getHeaders().containsKey("Access-Control-Allow-Origin"));
		
		
	
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}
	
	
}