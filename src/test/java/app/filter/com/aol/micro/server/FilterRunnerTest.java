package app.filter.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableMap;

public class FilterRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		Map<String, Filter> filters = ImmutableMap.of("/filter-app/status/ping2",new ConfiguredFilter());
		server = new MicroServerStartup( FilterAppLocalMain.class, ConfigurableModule.builder().context("filter-app").filters(filters ).build());
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void testAutoDiscoveredFilter() throws InterruptedException, ExecutionException{
		
		assertThat(MyFilter.getCalled(),is(0));
		assertThat(rest.get("http://localhost:8080/filter-app/status/ping"),is("ok"));
		assertThat(MyFilter.getCalled(),is(1));
	}
	@Test
	public void testConfiguredFilter() throws InterruptedException, ExecutionException{
		
		assertThat(ConfiguredFilter.getCalled(),is(0));
		assertThat(rest.get("http://localhost:8080/filter-app/status/ping2"),is("ok"));
		assertThat(ConfiguredFilter.getCalled(),is(1));
	}
	

	
	
}
