package app.listeners.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContextListener;

import nonautoscan.com.aol.micro.server.ConfiguredListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.boot.config.MicrobootApp;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableList;


@Microboot
public class ListenerRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicrobootApp server;
	@Before
	public void startServer(){
		List<ServletContextListener> listeners = ImmutableList.of(new ConfiguredListener());
		server = new MicrobootApp( ListenerRunnerTest.class, ConfigurableModule.builder().context("listener-app").listeners(listeners ).build());
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void testListeners() throws InterruptedException, ExecutionException{
		
		assertThat(AutodiscoveredListener.getCalled(),is(1));
		assertThat(ConfiguredListener.getCalled(),is(1));
	}
	
	

	
	
}
