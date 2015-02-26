package app.metrics.com.aol.micro.server;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.spring.metrics.CodahaleMetricsConfigurer;
import com.aol.micro.server.testing.RestAgent;
import com.codahale.metrics.ConsoleReporter;

@Configuration
@ComponentScan(basePackages = { "app.metrics.com.aol.micro.server" })
public class MetricsRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		CodahaleMetricsConfigurer.setInit( metricRegistry -> 		 TestReporter
		         .forRegistry(metricRegistry)
		         .build()
		         .start(10, TimeUnit.MILLISECONDS));
		
		server = new MicroServerStartup( MetricsRunnerTest.class, ()-> "simple-app");
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
