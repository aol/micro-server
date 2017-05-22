package app.custom.binder.direct;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class BinderDirectTest {
	RestAgent rest = new RestAgent();
	MicroserverApp server;
	@Before
	public void startServer(){
		
		
		server = new MicroserverApp(ConfigurableModule.builder().context("binder").build().<ResourceConfig>withResourceConfigManager(rc->rc.getJaxRsConfig().register(new CustomBinder4())));
	
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		DirectMyIncovationHandler.captured=false;
		assertThat(rest.get("http://localhost:8080/binder/test"),is("hello world!"));
		assertTrue(DirectMyIncovationHandler.captured);
		
		
	}
}
