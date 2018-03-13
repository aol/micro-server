package app.error.com.oath.micro.server;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.testing.RestAgent;

import javax.ws.rs.core.Response;

@Microserver
public class ErrorDetailsRunnerTest {

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

		Response response = rest.get("http://localhost:8080/simple-app/status/ping");
		assertThat(response.getStatus(),is(400));
		assertThat(response.readEntity(String.class), containsString("{\"errorCode\":\"EMPTY_REQUEST\",\"message\":\"Error id:"));
	}
}
