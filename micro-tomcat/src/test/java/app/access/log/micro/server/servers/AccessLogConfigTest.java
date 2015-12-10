package app.access.log.micro.server.servers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;

@Microserver(properties={"access.log.output", "${user.home}"})
public class AccessLogConfigTest {

	MicroserverApp server;

	File logFile;

	@Before
	public void startServer() throws IOException {

		logFile = new File(System.getProperty("user.home") + "/access-log-app-access.log");
		FileUtils.forceDelete(logFile);

		System.out.println(logFile.exists());
		assertThat(logFile.exists(), is(false));

		server = new MicroserverApp(() -> "access-log-app");
		server.start();

	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	public void runAppAndBasicTest() {

		assertThat(logFile.exists(), is(true));

	}
}
