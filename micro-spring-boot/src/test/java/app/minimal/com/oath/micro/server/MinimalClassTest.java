package app.minimal.com.oath.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.testing.RestAgent;
import com.oath.micro.server.spring.boot.MicroSpringBoot;

@Rest
@Path("/single") @MicroSpringBoot
public class MinimalClassTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp(()-> "minimal-app");
		

	}
	
	
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/minimal-app/single/ping"),is("ok1"));
	
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok1";
	}
	
	
}