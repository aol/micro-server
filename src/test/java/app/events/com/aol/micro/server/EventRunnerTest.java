package app.events.com.aol.micro.server;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.events.JobsBeingExecuted;
import com.aol.micro.server.rest.client.nio.RestClient;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class EventRunnerTest {

	RestAgent rest = new RestAgent();
	private final RestClient<String> client = new RestClient<String>(100,100).withAccept("application/json");
	MicroServerStartup server;
	
	
	@Before
	public void startServer(){
		
		server = new MicroServerStartup(()-> "event-app");
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
