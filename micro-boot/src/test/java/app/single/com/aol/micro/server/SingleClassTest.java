package app.single.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.boot.config.MicrobootApp;
import com.aol.micro.server.testing.RestAgent;

@Microboot
@Path("/single")
public class SingleClassTest implements RestResource{

	RestAgent rest = new RestAgent();
	
	MicrobootApp server;
	@Before
	public void startServer(){
		
		server = new MicrobootApp(  ()-> "simple-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/simple-app/single/ping"),is("ok"));
	
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}
	
	
}