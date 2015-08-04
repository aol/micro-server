package app.validation.com.aol.micro.server;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.InternalServerErrorException;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import jersey.repackaged.com.google.common.collect.ImmutableMultimap;
import jersey.repackaged.com.google.common.collect.ImmutableSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;
import com.aol.simple.react.stream.simple.SimpleReact;
import com.aol.simple.react.stream.traits.SimpleReactStream;

@Microserver(basePackages = { "app.guava.com.aol.micro.server" })
public class ValidationAppTest {

	RestAgent rest = new RestAgent();

	MicroserverApp server;

	ImmutableEntity entity;
	

	SimpleReact simpleReact = new SimpleReact();
	SimpleReactStream stream;

	@Before
	public void startServer() {
		stream = simpleReact.react(
				() -> server = new MicroserverApp(ValidationAppTest.class,
						() -> "guava-app")).then(server -> server.start());

		entity = ImmutableEntity.builder().value("value").build();

		
	}

	@After
	public void stopServer() {
		server.stop();
	}

	
	@Test(expected=InternalServerErrorException.class)
	public void confirmError() throws InterruptedException,
			ExecutionException {

		stream.block();
		rest.post(
				"http://localhost:8080/guava-app/status/ping", null,
				List.class);
		

	}
	@Test
	public void confirmNoError() throws InterruptedException,
			ExecutionException {

		stream.block();
		rest.post(
				"http://localhost:8080/guava-app/status/ping", entity,
				List.class);
		

	}

	

}
