package app.prototype.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class PrototypeLockCuratorRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer() throws IOException{
		FileUtils.deleteDirectory(new File("/tmp/zookeeper/pl"));
		new  Zookeeper().init();
		server = new MicroserverApp(()->"simple-app");
	
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{

		
		assertThat(rest.get("http://localhost:8080/simple-app/status/ping"),is("got"));
		assertThat(rest.get("http://localhost:8080/simple-app/status/ping"),is("got"));
		
		
	}
	
	
	
}
