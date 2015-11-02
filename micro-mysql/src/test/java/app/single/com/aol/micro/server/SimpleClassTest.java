package app.single.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.mysql.distlock.DistributedLockServiceMySqlImpl;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties={"db.connection.driver","org.hsqldb.jdbcDriver",
	    "db.connection.url","jdbc:hsqldb:mem:aname",
	    "db.connection.username", "sa",
	    "db.connection.password", "",
	    "db.connection.dialect","org.hibernate.dialect.HSQLDialect",
	    "db.connection.ddl.auto","create-drop"   })
public class SimpleClassTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	
	@Before
	public void startServer(){
		
		server = new MicroserverApp(()-> "simple-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		assertThat(rest.get("http://localhost:8080/simple-app/status/ping"),is("true"));
	
	}

	
	
	
}