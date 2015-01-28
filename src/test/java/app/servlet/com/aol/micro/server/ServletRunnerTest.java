package app.servlet.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.Servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;

public class ServletRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		Map<String, Servlet> servlets = new HashMap<>();
		servlets.put("/configured", new ConfiguredServlet());
		server = new MicroServerStartup( AppRunnerLocalMain.class, ConfigurableModule.builder().context("test-app").servlets(servlets  ).build());
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
	public void autoDiscoveredServletTest() throws InterruptedException, ExecutionException{
		
		
		assertThat(rest.get("http://localhost:8080/servlet"),is("hello world"));
	
	}
	
	@Test
	public void configuredServletTest() throws InterruptedException, ExecutionException{
		
		
		assertThat(rest.get("http://localhost:8080/configured"),is("configured servlet"));
	
	}
	
	
}
