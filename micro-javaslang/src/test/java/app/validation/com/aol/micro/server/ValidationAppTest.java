package app.validation.com.aol.micro.server;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.BadRequestException;

import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.javaslang.com.aol.micro.server.ImmutableJavaslangEntity;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.aol.micro.server.testing.RestAgent;
import com.aol.simple.react.stream.simple.SimpleReact;
import com.aol.simple.react.stream.traits.SimpleReactStream;


@Microserver(basePackages = { "app.validation.com.aol.micro.server" })
public class ValidationAppTest {

	RestAgent rest = new RestAgent();

	MicroserverApp server;

	ImmutableJavaslangEntity entity;
	

	SimpleReact simpleReact = new SimpleReact();
	SimpleReactStream stream;

	@Before
	public void startServer() {
		stream = simpleReact.react(
				() -> server = new MicroserverApp(ValidationAppTest.class,
						() -> "validation-app")).then(server -> server.start());

		entity = ImmutableJavaslangEntity.builder().value("value")
				.list(List.ofAll("hello", "world"))
				.mapOfSets(HashMap.<String,Set>empty().put("key1",HashSet.ofAll(Arrays.asList(1, 2, 3))))
				.build();
		
		String json = JacksonUtil.serializeToJson(entity);
		
		ImmutableJavaslangEntity entity  = JacksonUtil.convertFromJson(json, ImmutableJavaslangEntity.class);

		
	}

	@After
	public void stopServer() {
		server.stop();
	}

	
	@Test(expected=BadRequestException.class)
	public void confirmError() throws InterruptedException,
			ExecutionException {

		stream.block();
		rest.post(
				"http://localhost:8080/validation-app/status/ping", null,
				ImmutableJavaslangEntity.class);
		

	}
	@Test
	public void confirmNoError() throws InterruptedException,
			ExecutionException {

		stream.block();
		rest.post(
				"http://localhost:8080/validation-app/status/ping", entity,
				ImmutableJavaslangEntity.class);
		

	}

	

}
