package app.spring.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.boot.config.MicrobootApp;
import com.aol.micro.server.testing.RestAgent;
/**@Configuration
@ComponentScan(basePackages = { "app.spring.com.aol.micro.server" })**/
@Microboot
public class SpringRunnerTest {

	RestAgent rest = new RestAgent();
	
	@Bean
	public MyBean mybean(){
		return new MyBean();
	}
	
	MicrobootApp server;
	@Before
	public void startServer(){
		
		server = new MicrobootApp( SpringRunnerTest.class, ()-> "spring-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void testAutoWiring() throws InterruptedException, ExecutionException{
		
		assertThat(rest.get("http://localhost:8080/spring-app/spring/ping"),is("hello world"));
		
	}
	
	

	
	
}
