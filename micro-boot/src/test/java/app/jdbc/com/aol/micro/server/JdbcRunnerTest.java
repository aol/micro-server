package app.jdbc.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.boot.config.MicrobootApp;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.rest.client.nio.RestClient;
import com.aol.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableMap;

@Microboot @Ignore // Microboot datasources can be done Spring Boot way
public class JdbcRunnerTest {

  	private final RestClient<JdbcEntity> listClient = new RestClient(1000,1000).withResponse(JdbcEntity.class);

	RestAgent rest = new RestAgent();
	
	MicrobootApp server;
	
	@Before
	public void startServer(){
		
		
		server = new MicrobootApp( Config.instance().withEntityScan("app.jdbc.com.aol.micro.server")
												.withJdbcClasses(JdbcRunnerTest.class)
												.withProperties(
																			ImmutableMap.of("db.connection.driver","org.hsqldb.jdbcDriver",
																						    "db.connection.url","jdbc:hsqldb:mem:aname",
																						    "db.connection.username", "sa",
																						    "db.connection.dialect","org.hibernate.dialect.HSQLDialect",
																						    "db.connection.ddl.auto","create-drop"))
																				,()->"jdbc-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/jdbc-app/persistence/create"),is("ok"));
		assertThat(listClient.get("http://localhost:8080/jdbc-app/persistence/get").get(),is(JdbcEntity.class));
		
		
	}
	
	
	
}
