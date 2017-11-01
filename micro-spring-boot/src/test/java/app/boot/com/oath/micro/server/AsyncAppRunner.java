package app.boot.com.oath.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.oath.micro.server.MicroserverApp;

import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.spring.boot.MicroSpringBoot;

@Microserver @MicroSpringBoot
public class AsyncAppRunner {


	AsyncRestClient rest = new AsyncRestClient(1000,1000).withAccept("text/plain");
	
	
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{

		new MicroserverApp( ()-> "async-app");
		Thread.sleep(2000);
		
		assertThat(rest.get("http://localhost:8080/async-app/async/expensive").get(),is(";test!;test!;test!"));
	
	}
	
	
	
}