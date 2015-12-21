package app.level.checker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties = { "log4j.root.logger.checker.fixed.rate", "1500" })
public class Log4jRootLoggerChekerRunnerTest {

	RestAgent rest = new RestAgent();
	MicroserverApp server;

	@Before
	public void startServer() {
		server = new MicroserverApp(() -> "log4j");
		server.start();
	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	public void testChecker() throws InterruptedException {
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/change/to/warn"), is("WARN"));
		Thread.sleep(2000l);
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/get/level"), is("INFO"));
		
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/checker/level/DEBUG"), is("DEBUG"));
		Thread.sleep(2000l);
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/get/level"), is("DEBUG"));
		
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/checker/is/false"), is("false"));
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/checker/level/ERROR"), is("ERROR"));
		Thread.sleep(2000l);
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/get/level"), is("DEBUG"));
	}
	

}
