package app.validation.com.oath.micro.server;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.InternalServerErrorException;

import com.oath.cyclops.types.futurestream.SimpleReactStream;
import cyclops.async.SimpleReact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.guava.com.oath.micro.server.ImmutableGuavaEntity;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

@Microserver(basePackages = { "app.guava.com.oath.micro.server" })
public class ValidationAppTest {

	RestAgent rest = new RestAgent();

	MicroserverApp server;

	ImmutableGuavaEntity entity;
	

	SimpleReact simpleReact = new SimpleReact();
	SimpleReactStream stream;

	@Before
	public void startServer() {
		stream = simpleReact.ofAsync(
				() -> server = new MicroserverApp(ValidationAppTest.class,
						() -> "guava-app")).then(server -> server.start());

		entity = ImmutableGuavaEntity.builder().value("value")
				.list(ImmutableList.of("hello", "world"))
				.mapOfSets(ImmutableMap.of("key1", ImmutableSet.of(1, 2, 3)))
				.multiMap(ImmutableMultimap.of("1", 2, "1", 2, "2", 4)).build();

		
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
