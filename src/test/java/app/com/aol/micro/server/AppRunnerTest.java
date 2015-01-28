package app.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.testing.RestAgent;

public class AppRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		server = new MicroServerStartup( AppRunnerLocalMain.class, () -> "test-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		assertThat(rest.get("http://localhost:8080/test-app/status/ping"),is("ok"));
	
	}
	
	@Test
	public void servletTest() throws InterruptedException, ExecutionException{
		
		
		assertThat(rest.get("http://localhost:8080/servlet"),is("hello world"));
	
	}
	
	
}
