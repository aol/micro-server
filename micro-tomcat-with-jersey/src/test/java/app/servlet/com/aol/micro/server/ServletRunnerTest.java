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

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.testing.RestAgent;

public class ServletRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer() throws InterruptedException{
		Map<String, Servlet> servlets = new HashMap<>();
		servlets.put("/configured", new ConfiguredServlet());
		server = new MicroserverApp( AppRunnerLocalMain.class, ConfigurableModule.builder().context("test-app").servlets(servlets  ).build());
		Thread.sleep(1000);
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		assertThat(rest.get("http://localhost:8080/test-app/servlet/ping"),is("ok"));
	
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
