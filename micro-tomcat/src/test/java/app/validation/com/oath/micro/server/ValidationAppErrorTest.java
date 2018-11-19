package app.validation.com.oath.micro.server;

import com.oath.cyclops.types.futurestream.SimpleReactStream;
import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.testing.RestAgent;
import cyclops.futurestream.SimpleReact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import java.util.concurrent.ExecutionException;

//@Microserver(basePackages = { "app.guava.com.aol.micro.server" })
public class ValidationAppErrorTest {

	RestAgent rest = new RestAgent();

	MicroserverApp server;

	ImmutableEntity entity;
	

	SimpleReact simpleReact = new SimpleReact();
	SimpleReactStream stream;

	@Before
	public void startServer() throws InterruptedException {
		 server = new MicroserverApp(() -> "guava-app");
		 Thread.sleep(1000);
		 server.start();
		

		entity = ImmutableEntity.builder().value("value").build();

		
	}

	@After
	public void stopServer() {
		server.stop();
	}

	
	@Test(expected=BadRequestException.class)
	public void confirmError() throws InterruptedException,
			ExecutionException {

		
		//stream.block();
		
		rest.post(
				"http://localhost:8080/guava-app/status/ping", null,
				ImmutableEntity.class);
		

	}


}
