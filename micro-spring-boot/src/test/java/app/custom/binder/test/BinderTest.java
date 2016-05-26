package app.custom.binder.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.spring.boot.MicroSpringBoot;
import com.aol.micro.server.testing.RestAgent;

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
