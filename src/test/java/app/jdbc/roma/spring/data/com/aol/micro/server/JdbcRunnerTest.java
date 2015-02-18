package app.jdbc.roma.spring.data.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.config.Classes;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.client.nio.RestClient;
import com.aol.micro.server.testing.RestAgent;

@Microserver(springClasses = { Classes.JDBC_CLASSES,
		Classes.SPRING_DATA_CLASSES }, entityScan = "app.jdbc.roma.spring.data.com.aol.micro.server", properties = {
		"db.connection.driver", "org.hsqldb.jdbcDriver", "db.connection.url",
		"jdbc:hsqldb:mem:aname", "db.connection.username", "sa",
		"db.connection.dialect", "org.hibernate.dialect.HSQLDialect",
		"db.connection.ddl.auto", "create-drop" })
public class JdbcRunnerTest {

	private final RestClient<JdbcEntity> listClient = new RestClient(1000, 1000)
			.withResponse(JdbcEntity.class);

	RestAgent rest = new RestAgent();

	MicroServerStartup server;

	@Before
	public void startServer() {

		Config.reset();
		server = new MicroServerStartup(() -> "jdbc-app");
		server.start();

	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test @Ignore //spring data tests don't play well with other tests
	public void runAppAndBasicTest() throws InterruptedException,
			ExecutionException {

		
		assertThat(
				rest.get("http://localhost:8080/jdbc-app/persistence/create"),
				is("ok"));
		assertThat(
				listClient
						.get("http://localhost:8080/jdbc-app/persistence/get")
						.get(), is(JdbcEntity.class));

	}

}
