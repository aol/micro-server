package app.error.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class StatsRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		server = new MicroserverApp(ConfigurableModule
				.builder()
				.context("simple-app")
				.build());
	
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{


		if(new File(System.getProperty("java.library.path")).exists())
			assertThat(rest.get("http://localhost:8080/simple-app/stats/machine"),containsString("cpu-stats"));
		
		
	}
	
	
	
}
