package app.metrics.boot.com.oath.micro.server;


import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.boot.config.Microboot;
import com.oath.micro.server.spring.metrics.CodahaleMetricsConfigurer;
import com.oath.micro.server.testing.RestAgent;

@Microboot
public class MetricsRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		CodahaleMetricsConfigurer.setInit( metricRegistry -> 		 TestReporter
		         .forRegistry(metricRegistry)
		         .build()
		         .start(10, TimeUnit.MILLISECONDS));
		
		server = new MicroserverApp(  ()-> "simple-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException, IOException{
		
		
		
		
		
		assertThat(rest.get("http://localhost:8080/simple-app/metrics/ping"),is("ok"));
		
		
		assertThat(TestReporter.getTimer().size(),greaterThan(0));
	}
	
	
	
}
