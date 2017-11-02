package app.custom.binder.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.spring.boot.MicroSpringBoot;
import com.oath.micro.server.testing.RestAgent;

@MicroSpringBoot
public class BinderTest {
	RestAgent rest = new RestAgent();
	MicroserverApp server;
	@Before
	public void startServer(){
		server = new MicroserverApp(()->"binder");
	
	

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
