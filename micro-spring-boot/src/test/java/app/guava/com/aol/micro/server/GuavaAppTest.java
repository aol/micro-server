package app.guava.com.aol.micro.server;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.aol.micro.server.testing.RestAgent;
import com.aol.simple.react.stream.simple.SimpleReact;
import com.aol.simple.react.stream.traits.SimpleReactStream;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.aol.micro.server.spring.boot.MicroSpringBoot;
@Microserver @MicroSpringBoot
public class GuavaAppTest {

	RestAgent rest = new RestAgent();

	MicroserverApp server;

	ImmutableGuavaEntity entity;
	Jdk8Entity present;
	Jdk8Entity absent;

	SimpleReact simpleReact = new SimpleReact();
	SimpleReactStream stream;

	boolean run = false;
	@Before
	public void startServer() {
		if(run)
			return;
		run =true;
		stream = simpleReact.react(
				() -> server = new MicroserverApp(GuavaAppTest.class,
						() -> "guava-app"));

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
