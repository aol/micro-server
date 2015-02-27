package app.boot.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.boot.config.MicrobootApp;
import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.rest.client.nio.RestClient;
import com.aol.micro.server.testing.RestAgent;

@Microboot
public class AsyncAppRunner {


	RestClient rest = new RestClient(1000,1000).withAccept("text/plain");
	
	MicrobootApp server;
	@Before
	public void startServer(){
		
		server = new MicrobootApp( ()-> "async-app");
		server.start();
		

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		Thread.sleep(2000);
		
		assertThat(rest.get("http://localhost:8080/async-app/async/expensive").get(),is(";test!;test!;test!"));
	
	}
	
	
	
}