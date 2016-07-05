package app.couchbase.distributed.map.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.couchbase.mock.CouchbaseMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties = { "couchbaseServers", "http://localhost:8091/pools", "couchbasePassword", "",
		"couchbaseBucket", "beer-sample" })
public class CouchbaseRunnerTest {

	RestAgent rest = new RestAgent();

	MicroserverApp server;

	@Before
	public void startServer() {
		try {
			// couchbase already running?
			rest.get("http://localhost:8091/pools");
		} catch (Exception e) {
			// start mock couchbase
			CouchbaseMock.main(new String[] { "-S" });
		}
		server = new MicroserverApp(ConfigurableModule	.builder()
														.context("simple-app")
														.build());

		server.start();

	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	@Ignore
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException {
		rest.get("http://localhost:8080/simple-app/couchbase/put");
		assertThat(	rest.get("http://localhost:8080/simple-app/couchbase/get"),
					containsString("world"));

	}

}
