package app.hibernate.com.aol.micro.server;


import static com.aol.micro.server.spring.hibernate.HibernateConfigurer.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.rest.client.nio.RestClient;
import com.aol.micro.server.spring.annotations.Microserver;
import com.aol.micro.server.spring.hibernate.HibernateConfigurer;
import com.aol.micro.server.spring.hibernate.HibernateConfig;
import com.aol.micro.server.spring.properties.PropertyFileConfig;
import com.aol.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Microserver
public class HibernateRunnerTest {

  	private final RestClient<List<HibernateEntity>> listClient = new RestClient(1000,1000).withGenericResponse(List.class, HibernateEntity.class);

	RestAgent rest = new RestAgent();
	
	MicroServerStartup server;
	
	@Before
	public void startServer(){
		
		
		server = new MicroServerStartup( entityScan("app.hibernate.com.aol.micro.server").withHibernateClasses(HibernateRunnerTest.class).withProperties(
																			ImmutableMap.of("mdms.connection.driver","org.hsqldb.jdbcDriver",
																						    "mdms.connection.url","jdbc:hsqldb:mem:aname",
																						    "mdms.connection.username", "sa",
																						    "mdms.connection.dialect","org.hibernate.dialect.HSQLDialect",
																						    "mdms.connection.ddl.auto","create"))
																				,()->"hibernate-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/hibernate-app/persistence/create"),is("ok"));
		assertThat(listClient.get("http://localhost:8080/hibernate-app/persistence/get").get().get(0),is(HibernateEntity.class));
		
		
	}
	
	
	
}
