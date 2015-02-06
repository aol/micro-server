package app.single.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import app.simple.com.aol.micro.server.SimpleRunnerTest;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.testing.RestAgent;
@Configuration
@ComponentScan(basePackages = { "app.simple.com.aol.micro.server" })
@Component
@Path("/status")
public class SingleClassTest {

	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		
		server = new MicroServerStartup( SingleClassTest.class, ()-> "simple-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/simple-app/status/ping"),is("ok"));
	
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}
	
	
}