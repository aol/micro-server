package app.swagger.com.aol.micro.server;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.testing.RestAgent;
import com.aol.simple.react.SimpleReact;

@Configuration
@ComponentScan(basePackages = { "app.swagger.com.aol.micro.server" })
public class SwaggerRunnerTest {


	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		
		server = new MicroServerStartup( SwaggerRunnerTest.class, ()-> "swagger-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.getJson("http://localhost:8080/api-docs/stats"),containsString("Make a ping call"));
	
	}
	
	
	
}
