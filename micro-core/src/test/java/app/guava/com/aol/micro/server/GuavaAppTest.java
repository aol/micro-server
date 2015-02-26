package app.guava.com.aol.micro.server;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.JacksonUtil;
import com.aol.micro.server.testing.RestAgent;
import com.aol.simple.react.stream.simple.SimpleReact;
import com.aol.simple.react.stream.traits.SimpleReactStream;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

@Microserver(basePackages = { "app.guava.com.aol.micro.server" })
public class GuavaAppTest {

	RestAgent rest = new RestAgent();

	MicroServerStartup server;

	ImmutableGuavaEntity entity;
	Jdk8Entity present;
	Jdk8Entity absent;

	SimpleReact simpleReact = new SimpleReact();
	SimpleReactStream stream;

	@Before
	public void startServer() {
		stream = simpleReact.react(
				() -> server = new MicroServerStartup(GuavaAppTest.class,
						() -> "guava-app")).then(server -> server.start());

		entity = ImmutableGuavaEntity.builder().value("value")
				.list(ImmutableList.of("hello", "world"))
				.mapOfSets(ImmutableMap.of("key1", ImmutableSet.of(1, 2, 3)))
				.multiMap(ImmutableMultimap.of("1", 2, "1", 2, "2", 4)).build();

		JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(entity),
				ImmutableGuavaEntity.class);

		present = Jdk8Entity.builder().name(Optional.of("test")).build();

		JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(present),
				Optional.class);
		absent = Jdk8Entity.builder().name(Optional.empty()).build();
	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	public void confirmExpectedUrlsPresentTest() throws InterruptedException,
			ExecutionException {

		stream.block();

		assertThat((List<String>) rest.post(
				"http://localhost:8080/guava-app/status/ping", entity,
				List.class), hasItem("hello"));

	}

	@Test
	public void confirmOptionalConversionWorking() throws InterruptedException,
			ExecutionException {

		stream.block();

		assertThat(rest.post("http://localhost:8080/guava-app/status/optional",
				present, String.class), is("\"test\""));

		assertThat(rest.post("http://localhost:8080/guava-app/status/optional",
				absent, String.class), is("null"));

	}

}
