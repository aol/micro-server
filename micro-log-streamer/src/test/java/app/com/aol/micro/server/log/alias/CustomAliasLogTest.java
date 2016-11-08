package app.com.aol.micro.server.log.alias;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.reactive.EventQueueManager;
import com.aol.micro.server.reactive.rest.ReactiveRequest;
import com.aol.micro.server.testing.RestAgent;

@Microserver(properties = { "log.tailer.file.location", "/tmp/tailer-test-file" })
public class CustomAliasLogTest implements RestResource {

    RestAgent rest = new RestAgent();
    File testFile = new File(
                             "/tmp/tailer-test-file2");
    @Autowired
    EventQueueManager<String> manager;
    MicroserverApp server;

    static String lastRecieved = null;

    @Before
    public void startServer() throws IOException {
        lastRecieved = null;

        testFile.delete();
        server = new MicroserverApp(
                                    () -> "log-app");
        server.start();
        FileUtils.write(testFile, "hello world", "UTF-8", false);
        new Thread(
                   () -> {
                       for (int i = 0; i < 1_000; i++) {
                           try {
                               FileUtils.write(testFile, "new line " + i + "\n", "UTF-8", true);
                               Thread.sleep(1);
                           } catch (Exception e) {
                               // TODO Auto-generated catch block
                               e.printStackTrace();
                           }
                       }
                   }).start();
    }

    @After
    public void stopServer() {
        server.stop();
    }

    @PostConstruct
    public void busManager() {
        manager.forEach("ping", in -> lastRecieved = in);
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        int read = new ReactiveRequest(
                                       10000,
                                       10000).getTextStream("http://localhost:8080/log-app/log-tail/stream-file?alias=custom1")
                                             .limit(5)
                                             .toList()
                                             .size();

        assertThat(read, equalTo(5));

    }

}