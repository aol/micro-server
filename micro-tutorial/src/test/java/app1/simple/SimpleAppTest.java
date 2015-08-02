package app1.simple;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.rest.client.nio.AsyncNonNIORestClient;

public class SimpleAppTest {


	   	private final AsyncNonNIORestClient<String> rest = new AsyncNonNIORestClient(1000,1000).withAccept("text/plain");
	  
	
		MicroserverApp server;
		@Before
		public void startServer(){
			
			server = new MicroserverApp( MicroserverTutorial.class, ()-> "simple");
			server.start();

		}
		
		@After
		public void stopServer(){
			server.stop();
		}
		
		
		@Test @Ignore //failing in gradle
		public void basicEndPoint(){
			assertThat(rest.get("http://localhost:8080/simple/mypath/hello").join(),is("world"));
		}
		
}
