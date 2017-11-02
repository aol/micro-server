package app.swagger.com.oath.micro.server;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.testing.RestAgent;
import com.oath.micro.server.module.Module;
import com.oath.micro.server.spring.boot.MicroSpringBoot;

@Configuration
@ComponentScan(basePackages = { "app.swagger.com.oath.micro.server" })
@MicroSpringBoot // needs some work for spring-boot
public class SwaggerRunnerTest implements Module{


	
	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp( SwaggerRunnerTest.class,this);
		

	}
	
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.getJson("http://localhost:8080/swagger-app/stats/ping"),containsString("1"));
		assertThat(rest.getJson("http://localhost:8080/swagger-app/api-docs"),containsString("apiVersion"));
		
	}


	@Override
	public String getContext() {
		return "swagger-app";
	}
	
	
	
}
