package app.jdbc.transaction.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties={"db.connection.driver","org.hsqldb.jdbcDriver",
																						    "db.connection.url","jdbc:hsqldb:mem:aname",
																						    "db.connection.username", "sa",
											  })
public class JdbcRunnerTest {

  	private final AsyncRestClient<JdbcEntity> listClient = new AsyncRestClient(1000,1000).withResponse(JdbcEntity.class);

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	
	@Before
	public void startServer(){
		
		
		server = new MicroserverApp(()->"jdbc-app");
		server.start();
		rest.get("http://localhost:8080/jdbc-app/persistence/gen");
		
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
