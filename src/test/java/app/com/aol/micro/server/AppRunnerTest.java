package app.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.aol.micro.server.MicroServerStartup;
import com.aol.simple.react.SimpleReact;
import com.aol.simple.react.Stage;

public class AppRunnerTest {

	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		CompletableFuture f = new CompletableFuture();
		new SimpleReact().react(() -> new MicroServerStartup( AppRunnerLocalMain.class, () -> "test-app"))
				.then((server) -> { server.start(f); return 0;});
				
		f.get();
		
		
		assertThat(restCall("http://localhost:8080/test-app/status/ping"),is("ok"));
	
	}
	@Test
	public void servletTest() throws InterruptedException, ExecutionException{
		
		CompletableFuture f = new CompletableFuture();
		new SimpleReact().react(() -> new MicroServerStartup( AppRunnerLocalMain.class, () -> "test-app"))
				.then((server) -> { server.start(f); return 0;});
				
		f.get();
		
		
		assertThat(restCall("http://localhost:8080/servlet"),is("hello world"));
	
	}
	
	public String restCall(String url){
		

		
		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.TEXT_PLAIN);

		return request.get(String.class);
		
	}
}
