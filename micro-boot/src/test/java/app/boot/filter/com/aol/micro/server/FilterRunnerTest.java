package app.boot.filter.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableMap;

@Ignore
public class FilterRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		Map<String, Filter> filters = ImmutableMap.of("/filter-app/status/ping2",new ConfiguredFilter());
		server = new MicroserverApp( FilterAppLocalMain.class, ConfigurableModule.builder().context("filter-app").filters(filters ).build());
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void testAutoDiscoveredFilter() throws InterruptedException, ExecutionException{
		Thread.sleep(100);
		assertThat(AutodiscoveredFilter.getCalled(),is(0));
		assertThat(rest.get("http://localhost:8080/filter-app/status/ping"),is("ok"));
		assertThat(AutodiscoveredFilter.getCalled(),is(0));
	}
	@Test
	public void testConfiguredFilter() throws InterruptedException, ExecutionException{
		Thread.sleep(100);
		assertThat(ConfiguredFilter.getCalled(),is(0));
		assertThat(rest.get("http://localhost:8080/filter-app/status/ping2"),is("ok"));
		assertThat(ConfiguredFilter.getCalled(),is(1));
	}
	

	
	
}
