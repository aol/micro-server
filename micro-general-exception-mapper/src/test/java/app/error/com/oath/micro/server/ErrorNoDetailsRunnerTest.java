package app.error.com.oath.micro.server;


import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.testing.RestAgent;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Microserver(properties = {"micro.general.exception.mapper.details", "false"})
public class ErrorNoDetailsRunnerTest {

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
	public void runAppAndBasicTest(){
		Response response = rest.get("http://localhost:8080/simple-app/status/ping");
		assertThat(response.getStatus(),is(400));
		assertThat(response.readEntity(String.class), containsString("{\"errorCode\":\"INTERNAL_SERVER_ERROR\",\"message\":"));
	}
}
