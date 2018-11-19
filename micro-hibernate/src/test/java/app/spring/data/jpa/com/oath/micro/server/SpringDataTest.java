package app.spring.data.jpa.com.oath.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Config;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties={"db.connection.driver","org.hsqldb.jdbcDriver",
	    "db.connection.url","jdbc:hsqldb:mem:aname",
	    "db.connection.username", "sa",
	    "db.connection.dialect","org.hibernate.dialect.HSQLDialect",
	    "db.connection.ddl.auto","create-drop"}, entityScan="app.spring.data.jpa.com.oath.micro.server")
@EnableJpaRepositories
@Ignore
public class SpringDataTest {

  	private final AsyncRestClient<List<SpringDataEntity>> listClient = new AsyncRestClient(1000,1000).withGenericResponse(List.class, SpringDataEntity.class);

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	
	@Before
	public void startServer(){
		
		Config.reset();
		server = new MicroserverApp(()->"hibernate-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test  
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/hibernate-app/persistence/create"),is("ok"));
//works standalone, interferes with other tests		assertThat(listClient.get("http://localhost:8080/hibernate-app/persistence/get").get().get(0),is(SpringDataEntity.class));
		
		
	}
	
	
	
}
