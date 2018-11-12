package app.events.com.oath.micro.server;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.testing.RestAgent;

@Microserver
public class EventRunnerTest {

	RestAgent rest = new RestAgent();
	private final AsyncRestClient<String> client = new AsyncRestClient<String>(100,100).withAccept("application/json");
	MicroserverApp server;
	
	
	@Before
	public void startServer(){
		
		server = new MicroserverApp(()-> "event-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		

		assertThat(rest.get("http://localhost:8080/event-app/status/ping"),is("ok"));
		
		assertThat(client.get("http://localhost:8080/event-app/active/jobs").get(),
				containsString("startedAt"));
		assertThat(client.get("http://localhost:8080/event-app/active/requests").get(),
				containsString("startedAt"));
		assertThat(client.get("http://localhost:8080/event-app/manifest").get(),
				containsString("Manifest"));
		
	}
	
	
	
}
