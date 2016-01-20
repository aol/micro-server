package app.metrics.boot.com.aol.micro.server;


import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.spring.boot.MicroSpringBoot;
import com.aol.micro.server.spring.metrics.CodahaleMetricsConfigurer;
import com.aol.micro.server.testing.RestAgent;

@MicroSpringBoot @Microserver
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
		

	}
	
	
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException, IOException{
		
		
		
		
		
		assertThat(rest.get("http://localhost:8080/simple-app/metrics/ping"),is("ok"));
		
		
		assertThat(TestReporter.getTimer().size(),greaterThan(0));
	}
	
	
	
}
