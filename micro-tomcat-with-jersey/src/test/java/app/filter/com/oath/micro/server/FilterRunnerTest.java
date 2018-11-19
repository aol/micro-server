package app.filter.com.oath.micro.server;


import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.testing.RestAgent;
import com.oath.micro.server.utility.HashMapBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FilterRunnerTest {

	RestAgent rest = new RestAgent();
	
	static MicroserverApp server;
	@Before
	public void startServer() throws InterruptedException{
		Thread.sleep(500);
		Map<String, Filter> filters = HashMapBuilder.<String, Filter>map("/filter-app/status/ping2",new ConfiguredFilter()).build();
		server = new MicroserverApp(ConfigurableModule.builder()
													.context("filter-app")
													.filters(filters )
													.requestListeners(Arrays.asList(new org.springframework.web.context.request.RequestContextListener())).build());
		server.start();

	}
	
	@AfterClass
	public static void stopServer(){
		server.stop();
	}
	
	@Test
	public void testAutoDiscoveredFilter() throws InterruptedException, ExecutionException{
		AutodiscoveredFilter.setCalled(0);
		
		assertThat(rest.get("http://localhost:8080/filter-app/status/ping"),is("ok"));
		assertThat(AutodiscoveredFilter.getCalled(),is(1));
		assertThat(AutodiscoveredFilter.isBeanSet(),is(true));
	}
	@Test
	public void testConfiguredFilter() throws InterruptedException, ExecutionException{
		
		assertThat(ConfiguredFilter.getCalled(),is(0));
		assertThat(rest.get("http://localhost:8080/filter-app/status/ping2"),is("ok"));
		assertThat(ConfiguredFilter.getCalled(),is(1));
	}
	

	
	
}
