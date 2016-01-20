package app.simple.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.spring.boot.MicroSpringBoot;
import com.aol.micro.server.testing.RestAgent;
import com.aol.micro.server.spring.boot.MicroSpringBoot;

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
