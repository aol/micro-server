package app.jackson.includes.all.com.aol.micro.server.copy;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties={"jackson.serialization","ALWAYS"})
public class IncludesRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer() throws InterruptedException{
		Thread.sleep(5000);
		server = new MicroserverApp(ConfigurableModule
				.builder()
				.context("simple-app")
				.defaultResources(Arrays.asList(MultiPartFeature.class))
				.build());
		
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{

		
		
		assertThat(rest.getJson("http://localhost:8080/simple-app/status/ping"),is("{\"primitive\":null}"));
		
		
	}
	
	
	
}
