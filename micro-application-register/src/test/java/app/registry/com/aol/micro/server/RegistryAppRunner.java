package app.registry.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.spring.properties.PropertyFileConfig;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties={"service.registry.url","http://localhost:8080/registry-app"})
public class RegistryAppRunner {


	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp( ()-> "registry-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		Thread.sleep(1000);
		
		assertThat(rest.post("http://localhost:8080/registry-app/service-registry/schedule"),is("{\"status\":\"success\"}"));
		Thread.sleep(1000);
		assertThat(rest.getJson("http://localhost:8080/registry-app/service-registry/list"),containsString("[{\"port\":8080,"));
	
	}
	
	
	
	
	
}