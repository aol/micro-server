package app.singleton.com.aol.micro.server.copy;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class KeepLockCuratorRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	@Before
	public void startServer() throws IOException{
		FileUtils.deleteDirectory(new File("/tmp/zookeeper/kl"));
		
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

		
		assertThat(rest.get("http://localhost:8080/simple-app/status/lock"),is("got"));
		assertThat(rest.get("http://localhost:8080/simple-app/status/lock"),is("got"));
		assertThat(rest.get("http://localhost:8080/simple-app/status/lock2"),is("not"));		
		
	}
	
	
	
}
