package app.events.com.aol.micro.server;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.rest.JacksonUtil;
import com.aol.micro.server.rest.jersey.JacksonFeature;
import com.aol.micro.server.testing.RestAgent;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Microserver
public class EventRunnerTest {

	RestAgent rest = new RestAgent();
	MicroserverApp server;
	
	
	@Before
	public void startServer(){
		
		server = new MicroserverApp(()-> "event-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/event-app/status/ping"),is("ok"));
		
		assertThat(rest.getJson("http://localhost:8080/event-app/active/jobs"),
				containsString("startedAt"));
		assertThat(rest.getJson("http://localhost:8080/event-app/active/requests"),
				containsString("startedAt"));
		assertThat(rest.getJson("http://localhost:8080/event-app/manifest"),
				containsString("Manifest"));
		
	}
	protected Client initClient(int rt, int ct) {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, ct);
		clientConfig.property(ClientProperties.READ_TIMEOUT, rt);

		ClientBuilder.newBuilder().register(JacksonFeature.class);
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(JacksonFeature.class);
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(JacksonUtil.getMapper());
		client.register(provider);
		return client;

	}
	
	
}
