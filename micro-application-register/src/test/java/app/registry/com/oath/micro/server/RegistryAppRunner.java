package app.registry.com.oath.micro.server;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.application.registry.RegisterEntry;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.rest.jackson.JacksonUtil;
import com.oath.micro.server.testing.RestAgent;

@Microserver(properties = {
    "service.registry.url",
    "http://localhost:8080/registry-app",
    "target.endpoint",
    "configured-target"
})
public class RegistryAppRunner {

    private final AsyncRestClient restAsync = new AsyncRestClient(100, 2000);
    RestAgent rest = new RestAgent();
    MicroserverApp server;

    String baseUrl = "http://localhost:8080/registry-app/service-registry";

    @Before
    public void startServer() {
        server = new MicroserverApp(() -> "registry-app");
        server.start();
    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        SimpleDateFormat f = new SimpleDateFormat("EEE");
        String date = f.format(new Date());
        Thread.sleep(1000);

        assertThat(rest.post(baseUrl + "/schedule"), is("{\"status\":\"success\"}"));
        Thread.sleep(1000);

        String listResponse = rest.getJson(baseUrl + "/list");

        assertThat(listResponse, containsString("[{\"port\":8080,"));
        assertThat(listResponse, containsString("externalPort\":8080"));

        sendPing("1", 8081, "use-ip", "hello", "world", "my-target", 8082);
        Thread.sleep(1000);

        listResponse = rest.getJson(baseUrl + "/list");

        assertThat(listResponse, containsString("{\"port\":8081,"));
        assertThat(listResponse, containsString("\"target\":\"my-target\""));
        assertThat(listResponse, containsString("\"target\":\"configured-target\""));
        assertThat(listResponse, not(containsString("\"hostname\":\"test-host\"")));
        assertThat(listResponse, containsString("\"formattedDate\""));
        assertThat(listResponse, containsString("\"manifest\""));
        assertThat(listResponse, containsString("Manifest-Version"));
        assertThat(listResponse, containsString(date));

    }

    @Test
    public void filterTest() throws Exception {
        Thread.sleep(1000);

        List<RegisterEntry> entries = list();
        assertThat(entries.size(), is(1));

        sendPing("121", 8080, "host1", "module1", "context1", "target1", 9080);
        sendPing("122", 8080, "host2", "module1", "context1", "target1", 9080);
        sendPing("131", 6080, "host3", "module2", "context2", "target2", 7080);
        sendPing("132", 6080, "host4", "module2", "context2", "target2", 7080);

        entries = list();
        assertThat(entries.size(), is(5));

        entries = list("port=8080");
        assertThat(entries.size(), is(3));

        entries = list("port=8080", "externalPort=9080");
        System.out.println(entries);
        assertThat(entries.size(), is(2));

        entries = list("port=8080", "externalPort=9080", "module=module", "context=context1");
        assertThat(entries.size(), is(2));

        entries = list("port=8080", "externalPort=9080", "module=module", "context=context1",
            "hostname=host1");
        assertThat(entries.size(), is(1));

        entries = list("port=8080", "externalPort=9080", "module=module1", "context=context2");
        assertThat(entries.size(), is(0));

        entries = list("manifest.Implementation-Version=version");
        assertThat(entries.size(), is(4));

        entries = list("manifest.Implementation-Version=version1");
        assertThat(entries.size(), is(4));

        entries = list("manifest.Implementation-Version=version121");
        assertThat(entries.size(), is(1));

        entries = list("manifest.Implementation-revision=rev12");
        assertThat(entries.size(), is(2));

        entries = list("manifest.Implementation-Timestamp=2017_13");
        assertThat(entries.size(), is(2));

        entries = list("health=OK");
        assertThat(entries.size(), is(5));

        List<String> list = JacksonUtil.convertFromJson(rest.getJson(baseUrl + "/list?port=OK"),
            new TypeReference<List<String>>() {
            });
        assertThat(list.size(), is(1));
        assertThat(list.get(0), is("Bad Request: 'OK' is not a valid number."));

        list = JacksonUtil.convertFromJson(rest.getJson(baseUrl + "/list?health=Suspended"),
            new TypeReference<List<String>>() {
            });
        assertThat(list.size(), is(1));
        assertThat(list.get(0),
            is("Bad Request: 'Suspended' is not valid, valid values are [OK, ERROR]"));
    }

    private List<RegisterEntry> list(String... parameters) {
        String url = baseUrl + "/list?" + Stream.of(parameters).collect(joining("&"));
        return JacksonUtil
            .convertFromJson(rest.getJson(url), new TypeReference<List<RegisterEntry>>() {
            });
    }

    private void sendPing(String uuid, int port, String hostName, String module, String context,
        String target, int externalPort) {
        try {
            RegisterEntry re = RegisterEntry.builder()
                .port(port)
                .hostname(hostName)
                .module(module)
                .context(context)
                .time(new Date())
                .uuid(uuid)
                .target(target)
                .externalPort(externalPort)
                .build();
            re.getManifest().put("Implementation-revision", "rev" + uuid);
            re.getManifest().put("Implementation-Version", "version" + uuid);
            re.getManifest().put("Implementation-Timestamp", "2017_" + uuid);
            restAsync.post("http://localhost:8080/registry-app/service-registry/register",
                JacksonUtil.serializeToJson(re))
                .get();
        } catch (Exception e) {
        }
    }
}
