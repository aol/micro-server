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

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.boot.config.Microboot;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableList;


@Microserver @Microboot
public class ListenerRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer(){
		List<ServletContextListener> listeners = ImmutableList.of(new ConfiguredListener());
		server = new MicroserverApp( ListenerRunnerTest.class, ConfigurableModule.builder().context("listener-app").listeners(listeners ).build());
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
