package app.async.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import app.simple.com.aol.micro.server.SimpleRunnerTest;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.testing.RestAgent;
@Configuration
@ComponentScan(basePackages = { "app.async.com.aol.micro.server" })

public class AsyncAppRunner {


	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		
		server = new MicroServerStartup( AsyncAppRunner.class, ()-> "async-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/async-app/async/expensive"),is(";test!;test!;test!"));
	
	}
	
	
	
}