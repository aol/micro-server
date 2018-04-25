package app.root.context.single;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Microserver
@Path("/single")
public class RootSingleClassTest implements RestResource{

	RestAgent rest = new RestAgent();

	MicroserverApp server;
	@Before
	public void startServer(){

		server = new MicroserverApp( RootSingleClassTest.class, ()-> "");
		server.start();

	}

	@After
	public void stopServer(){
		server.stop();
	}

	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{



		assertThat(rest.get("http://localhost:8080/single/ping"),is("ok"));

	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "ok";
	}


}
