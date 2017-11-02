package app.async.com.oath.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.spring.properties.PropertyFileConfig;
import com.oath.micro.server.testing.RestAgent;

@Microserver
public class AsyncAppRunner {


	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer() throws InterruptedException{
		
		server = new MicroserverApp( ()-> "async-app");
		Thread.sleep(1000);
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		Thread.sleep(2000);
		
		assertThat(rest.get("http://localhost:8080/async-app/async/expensive"),is(";test!;test!;test!"));
	
	}
	
	@Test
	public void loadProperties() throws IOException{
		
		 Properties props = new PropertyFileConfig(true).propertyFactory() ;
		assertThat(props.getProperty("test"),is("hello world"));
	}
	
	
	
}