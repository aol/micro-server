package app.single.serverconfig.com.oath.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.testing.RestAgent;


@Path("/single")
public class SingleClassTest implements RestResource{

	RestAgent rest = new RestAgent();
	
	boolean called;
	MicroserverApp server;
	@Before
	public void startServer() throws InterruptedException{
		called = false;
		server = new MicroserverApp( ConfigurableModule.builder()
				.context("hello")
				.serverConfigManager(server->called=true)
				.build());
		Thread.sleep(1000);
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/hello/single/ping"),is("ok"));
		assertTrue(called);
	
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}
	
	
}