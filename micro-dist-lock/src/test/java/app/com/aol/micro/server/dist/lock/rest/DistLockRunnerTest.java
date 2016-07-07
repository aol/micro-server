package app.com.aol.micro.server.dist.lock.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class DistLockRunnerTest {

    RestAgent rest = new RestAgent();
    MicroserverApp server;

    @Before
    public void startServer() {
        server = new MicroserverApp(
                                    () -> "test-app");
        server.start();
    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void testOwnLock() {
        assertThat(rest.getJson("http://localhost:8080/test-app/lock-owner/dummy-key"), is("true"));
        assertThat(rest.getJson("http://localhost:8080/test-app/lock-owner/dummyKeyProvider2"), is("false"));

    }
}
