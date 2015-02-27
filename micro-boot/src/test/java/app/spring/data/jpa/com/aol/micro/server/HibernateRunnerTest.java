package app.spring.data.jpa.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.boot.config.MicrobootApp;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.rest.client.nio.RestClient;
import com.aol.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableMap;

@Microboot
@EnableJpaRepositories  @Ignore // Microboot datasources can be done Spring Boot way - test to be changed
public class HibernateRunnerTest {

  	private final RestClient<List<HibernateEntity>> listClient = new RestClient(1000,1000).withGenericResponse(List.class, HibernateEntity.class);

	RestAgent rest = new RestAgent();
	
	MicrobootApp server;
	
	@Before
	public void startServer(){
		
		Config.reset();
		server = new MicrobootApp( Config.instance().withEntityScan("app.spring.data.jpa.com.aol.micro.server")
												.withHibernateClasses(HibernateRunnerTest.class)
												.withProperties(
																			ImmutableMap.of("db.connection.driver","org.hsqldb.jdbcDriver",
																						    "db.connection.url","jdbc:hsqldb:mem:aname",
																						    "db.connection.username", "sa",
																						    "db.connection.dialect","org.hibernate.dialect.HSQLDialect",
																						    "db.connection.ddl.auto","create-drop"))
																				,()->"hibernate-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test @Ignore //spring data tests don't play well with other tests
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/hibernate-app/persistence/create"),is("ok"));
		assertThat(listClient.get("http://localhost:8080/hibernate-app/persistence/get").get().get(0),is(HibernateEntity.class));
		
		
	}
	
	
	
}
