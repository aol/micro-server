package app.jackson.custom.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class CustomRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
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

		
		assertThat(rest.postString("http://localhost:8080/simple-app/status/ping","{\"primitive\":null}").getStatus(),is(500));
		
		
	}
	
	
	
}
