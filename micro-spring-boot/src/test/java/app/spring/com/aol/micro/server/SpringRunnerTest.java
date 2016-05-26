package app.spring.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.spring.boot.MicroSpringBoot;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

@Microserver @MicroSpringBoot
public class SpringRunnerTest {

	RestAgent rest = new RestAgent();
	
	@Bean
	public MyBean mybean(){
		return new MyBean();
	}
	
	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp( SpringRunnerTest.class, ()-> "spring-app");
		

	}
	
	
	@Test
	public void testAutoWiring() throws InterruptedException, ExecutionException{
		
		assertThat(rest.get("http://localhost:8080/spring-app/spring/ping"),is("hello world"));
		
	}
	
	

	
	
}
