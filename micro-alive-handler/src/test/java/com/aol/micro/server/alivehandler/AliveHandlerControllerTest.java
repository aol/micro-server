package com.aol.micro.server.alivehandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;

import jersey.repackaged.com.google.common.collect.ImmutableMap;

@Microserver
public class AliveHandlerControllerTest {
	private MicroserverApp server;

	@Before
	public void startServer() throws InterruptedException {
		server = new MicroserverApp(ConfigurableModule.builder().context("simple-app")
				.restResourceClasses(Collections.singletonList(AliveHandlerRest.class))
				.defaultResources(Arrays.asList(MultiPartFeature.class)).build());
		Thread.sleep(1000);
		server.start();
	}
	
	@After
	public void stopServer() {
		server.stop();
	}

	@Bean
	public AliveHandlerControllerRepository getAliveHandlerControllerRepository() {

		final AliveHandlerController controller = new AliveHandlerController() {

			@Override
			public Response process() {
				return Response.ok().build();
			}

			@Override
			public void enable() {
				// TODO Auto-generated method stub

			}

			@Override
			public void disable() {
				// TODO Auto-generated method stub

			}
		};

		return new AliveHandlerControllerRepository() {

			private Map<String, AliveHandlerController> map = ImmutableMap.of("ping", controller);

			@Override
			public Optional<AliveHandlerController> get(String name) {
				return Optional.ofNullable(map.get(name));
			}
		};

	}

	@Test
	public void alive() throws Exception {
		Assert.assertEquals(200, getStatusCode("http://localhost:8080/simple-app/ping"));
	}
	
	@Test
	public void dead() throws Exception {
		Assert.assertEquals(404, getStatusCode("http://localhost:8080/simple-app/wrong_ping"));
	}
	
	private int getStatusCode(String urlString) throws Exception {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		return conn.getResponseCode();
	}
}
