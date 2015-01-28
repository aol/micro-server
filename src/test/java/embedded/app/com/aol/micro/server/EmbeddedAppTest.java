package embedded.app.com.aol.micro.server;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.NotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.module.EmbeddedModule;
import com.aol.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableList;

public class EmbeddedAppTest {
	
	RestAgent rest = new RestAgent();
	MicroServerStartup server;
	@Before
	public void startServer(){
		server = new MicroServerStartup(EmbeddedAppLocalMain.class, 
				new EmbeddedModule(TestAppRestResource.class,"test-app"),
				new EmbeddedModule(AltAppRestResource.class,"alternative-app"));
		server.start();
	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void confirmExpectedUrlsPresentTest() throws InterruptedException, ExecutionException{
		
		assertThat(rest.get("http://localhost:8080/test-app/test-status/ping"),is("test!"));
		
		
		assertThat((List<String>)rest.post("http://localhost:8081/alternative-app/alt-status/ping",new ImmutableEntity("value",ImmutableList.of("hello","world")),List.class),
				hasItem("hello"));
	
	}
	
	@Test(expected=NotFoundException.class)
	public void confirmAltAppCantUseTestAppResources(){
		
		assertThat(rest.get("http://localhost:8080/alternative-app/test-status/ping"),is("test!"));
	
	}
	@Test(expected=NotFoundException.class)
	public void confirmTestAppCantUseAltAppResources(){
		
		assertThat((List<String>)rest.post("http://localhost:8081/test-app/alt-status/ping",new ImmutableEntity("value",ImmutableList.of("hello","world")),List.class),
				hasItem("hello"));
	
	}
}
