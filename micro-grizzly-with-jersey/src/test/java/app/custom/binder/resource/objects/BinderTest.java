package app.custom.binder.resource.objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class BinderTest {
	RestAgent rest = new RestAgent();
	MicroserverApp server;
	@Before
	public void startServer(){
		
		
		server = new MicroserverApp(ConfigurableModule.builder().context("binder").jaxRsResourceObjects(PSetX.of(new CustomBinder3())).build());
	
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
