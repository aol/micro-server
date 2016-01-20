package app.boot.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;

import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.client.nio.AsyncRestClient;
import com.aol.micro.server.spring.boot.MicroSpringBoot;

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