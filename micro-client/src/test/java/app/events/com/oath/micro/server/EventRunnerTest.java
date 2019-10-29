package app.events.com.oath.micro.server;


import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.testing.RestAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Microserver
public class EventRunnerTest {

	RestAgent rest = new RestAgent();
	private final AsyncRestClient<String> client = new AsyncRestClient<String>(30000,30000).withAccept("application/json");
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


        System.out.println("Res  ****");//  + client.get("http://localhost:8080/event-app/active/jobs").get());
		System.out.println(client.get("http://localhost:8080/event-app/active/jobs").get());
		System.out.println("****");

		assertThat(client.get("http://localhost:8080/event-app/active/jobs").get(),
				containsString("startedAt"));
		assertThat(client.get("http://localhost:8080/event-app/active/requests").get(),
				containsString("startedAt"));
		assertThat(client.get("http://localhost:8080/event-app/manifest").get(),
				containsString("Manifest"));

		
	}
	
	
	
}
