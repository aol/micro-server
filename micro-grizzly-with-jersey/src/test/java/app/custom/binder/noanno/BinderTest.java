package app.custom.binder.noanno;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class BinderTest {
	RestAgent rest = new RestAgent();
	MicroserverApp server;
	@Before
	public void startServer(){
		server = new MicroserverApp(()->"binder");
	
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		MyIncovationHandler.captured=false;
		assertThat(rest.get("http://localhost:8080/binder/test"),is("hello world!"));
		assertTrue(MyIncovationHandler.captured);
		
		
	}
}
