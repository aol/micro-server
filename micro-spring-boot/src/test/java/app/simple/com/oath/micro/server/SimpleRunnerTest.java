package app.simple.com.oath.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.spring.boot.MicroSpringBoot;
import com.oath.micro.server.testing.RestAgent;

@Microserver @MicroSpringBoot
public class SimpleRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp( SimpleRunnerTest.class, ()-> "simple-app");
		

	}
	
	
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/simple-app/status/ping"),is("ok"));
		
		
	}
	
	
	
}
