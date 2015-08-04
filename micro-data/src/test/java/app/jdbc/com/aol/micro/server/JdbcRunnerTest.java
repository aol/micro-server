package app.jdbc.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.client.nio.AsyncRestClient;
import com.aol.micro.server.testing.RestAgent;

@Microserver(entityScan="app.jdbc.com.aol.micro.server",properties={"db.connection.driver","org.hsqldb.jdbcDriver",
																						    "db.connection.url","jdbc:hsqldb:mem:aname",
																						    "db.connection.username", "sa",
																						    "db.connection.dialect","org.hibernate.dialect.HSQLDialect",
																						    "db.connection.ddl.auto","create-drop"   })
public class JdbcRunnerTest {

  	private final AsyncRestClient<JdbcEntity> listClient = new AsyncRestClient(1000,1000).withResponse(JdbcEntity.class);

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	
	@Before
	public void startServer(){
		
		
		server = new MicroserverApp(()->"jdbc-app");
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
