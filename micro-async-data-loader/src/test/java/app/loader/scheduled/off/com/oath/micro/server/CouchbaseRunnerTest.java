package app.loader.scheduled.off.com.oath.micro.server;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.couchbase.mock.CouchbaseMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.ConfigurableModule;
import com.oath.micro.server.rest.jackson.JacksonUtil;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties = {
    "couchbaseServers", "http://localhost:8091/pools",
    "couchbasePassword", "",
    "couchbaseBucket", "beer-sample",
    "async.data.schedular.cron.loader", "* * * * * ?",
    "async.data.schedular.cron.cleaner", "* * * * * ?"
})
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
            CouchbaseMock.main(new String[]{"-S"});
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
        rest.get("http://localhost:8080/simple-app/couchbase/put");
        assertThat(rest.get("http://localhost:8080/simple-app/couchbase/get"),
            containsString("world"));

        Thread.sleep(2000);
        String json = rest.getJson("http://localhost:8080/simple-app/couchbase/loading-events");
        List list = JacksonUtil.convertFromJson(json, List.class);
        System.out.println(list);
        assertTrue(list.size() == 0);

    }

}
