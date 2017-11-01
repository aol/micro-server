package app.s3.manifest.comparator.com.aol.micro.server.second;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.oath.micro.server.testing.RestAgent;

public class ManifestComparatorRunnerTest {

    RestAgent rest = new RestAgent();

    MicroserverApp server;

    @Before
    public void startServer() {

        server = new MicroserverApp(
                                    () -> "simple-app");

        server.start();

    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    @Ignore
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
