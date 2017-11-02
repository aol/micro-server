package app.level.setter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties = { "log4j.root.logger.checker.active", "false" })
public class Log4jRootLoggerRunnerTest {

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
	public void testChangeToWarn() {
		assertThat(rest.get("http://localhost:8080/log4j/log4j/rootlogger/change/to/warn"), is("WARN"));
	}
	

}
