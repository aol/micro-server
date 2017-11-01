package app.level.checker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties = { "logback.root.logger.checker.fixed.rate", "1500" })
public class LogbackRootLoggerChekerRunnerTest {

	RestAgent rest = new RestAgent();
	MicroserverApp server;

	@Before
	public void startServer() {
		server = new MicroserverApp(() -> "logback");
		server.start();
	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	public void testChecker() throws InterruptedException {
		assertThat(rest.get("http://localhost:8080/logback/logback/rootlogger/change/to/warn"), is("WARN"));
		Thread.sleep(2000l);
		assertThat(rest.get("http://localhost:8080/logback/logback/rootlogger/get/level"), is("INFO"));
		
		assertThat(rest.get("http://localhost:8080/logback/logback/rootlogger/checker/level/DEBUG"), is("DEBUG"));
		Thread.sleep(2000l);
		assertThat(rest.get("http://localhost:8080/logback/logback/rootlogger/get/level"), is("DEBUG"));
		
		assertThat(rest.get("http://localhost:8080/logback/logback/rootlogger/checker/is/false"), is("false"));
		assertThat(rest.get("http://localhost:8080/logback/logback/rootlogger/checker/level/ERROR"), is("ERROR"));
		Thread.sleep(2000l);
		assertThat(rest.get("http://localhost:8080/logback/logback/rootlogger/get/level"), is("DEBUG"));
	}
	

}
