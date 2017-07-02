package app.couchbase.manifest.comparator.com.aol.micro.server.second;

import static org.hamcrest.CoreMatchers.equalTo;
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
public class ManifestComparatorRunnerTest {

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
        server = new MicroserverApp(
                                    ConfigurableModule.builder()
                                                      .context("simple-app")
                                                      .build());

        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {
        rest.get("http://localhost:8080/simple-app/comparator/increment");

        assertThat(rest.get("http://localhost:8080/simple-app/comparator/check"), equalTo("true"));
        assertThat(rest.get("http://localhost:8080/simple-app/comparator/get"), equalTo("hello1"));
        rest.get("http://localhost:8080/simple-app/comparator/increment");
        assertThat(rest.get("http://localhost:8080/simple-app/comparator/get"), equalTo("hello2"));

        rest.get("http://localhost:8080/simple-app/comparator2/increment");

        assertThat(rest.get("http://localhost:8080/simple-app/comparator/check"), equalTo("false"));
        assertThat(rest.get("http://localhost:8080/simple-app/comparator/get"), equalTo("hellob"));

    }

}
