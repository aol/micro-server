
package app.rest.client.com.oath.micro.server;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.RestClient;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.rest.client.nio.NIORestClient;
import com.oath.micro.server.rest.client.nio.SpringConfig;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

@Microserver
public class RestClientTest {

   	private final AsyncRestClient<List<String>> listClient = new AsyncRestClient(1000,1000).withResponse(List.class);
	private final AsyncRestClient<ImmutableSet<String>> setClient = new AsyncRestClient(1000,1000).withResponse(ImmutableSet.class);;
	private final AsyncRestClient<ImmutableList<MyEntity>> genericsClient = new AsyncRestClient(1000,1000).withGenericResponse(ImmutableList.class, MyEntity.class);
 
  	private final RestClient<List<String>> listClientSync = new RestClient(1000,1000).withResponse(List.class);
	private final RestClient<ImmutableSet<String>> setClientSync = new RestClient(1000,1000).withResponse(ImmutableSet.class);;
	private final RestClient<ImmutableList<MyEntity>> genericsClientSync = new RestClient(1000,1000).withGenericResponse(ImmutableList.class, MyEntity.class);
 
	private final NIORestClient rest = new SpringConfig().restClient();
																			

	MicroserverApp server;
	@Before
	public void startServer(){
		
		server = new MicroserverApp( RestClientTest.class, ()-> "rest-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	/*
	 * Simpler with JaxRsNIOClient
	 */
	@Test
	public void testCRUD() throws InterruptedException, ExecutionException{
		
		
		assertThat(listClient.get("http://localhost:8080/rest-app/rest/get").get().get(0),is("ok"));
		assertThat(setClient.post("http://localhost:8080/rest-app/rest/post",ImmutableMap.of(1,"hello")).get(),is(ImmutableSet.of("hello")));
		assertThat(setClient.put("http://localhost:8080/rest-app/rest/put",ImmutableMap.of(1,"hello")).get(),is(ImmutableSet.of("hello")));
		assertThat(listClient.delete("http://localhost:8080/rest-app/rest/delete").get().get(0),is("ok"));
	}
	
	@Test
	public void testCRUDGenerics() throws InterruptedException, ExecutionException{
		
		
		assertThat(genericsClient.get("http://localhost:8080/rest-app/generics/get").get().get(0),is(new MyEntity()));
		assertThat(genericsClient.post("http://localhost:8080/rest-app/generics/post",ImmutableMap.of(1,"hello")).get(),is(ImmutableList.of(new MyEntity())));
		assertThat(genericsClient.put("http://localhost:8080/rest-app/generics/put",ImmutableMap.of(1,"hello")).get(),is(ImmutableList.of(new MyEntity())));
		assertThat(genericsClient.delete("http://localhost:8080/rest-app/generics/delete").get().get(0),is(new MyEntity()));
	}
	/*
	 * Simpler with JaxRsNIOClient
	 */
	@Test
	public void testCRUDSync() throws InterruptedException, ExecutionException{
		
		
		assertThat(listClientSync.get("http://localhost:8080/rest-app/rest/get").get(0),is("ok"));
		assertThat(setClientSync.post("http://localhost:8080/rest-app/rest/post",ImmutableMap.of(1,"hello")),is(ImmutableSet.of("hello")));
		assertThat(setClientSync.put("http://localhost:8080/rest-app/rest/put",ImmutableMap.of(1,"hello")),is(ImmutableSet.of("hello")));
		assertThat(listClientSync.delete("http://localhost:8080/rest-app/rest/delete").get(0),is("ok"));
	}
	
	@Test
	public void testCRUDGenericsSync() throws InterruptedException, ExecutionException{
		
		
		assertThat(genericsClientSync.get("http://localhost:8080/rest-app/generics/get").get(0),is(new MyEntity()));
		assertThat(genericsClientSync.post("http://localhost:8080/rest-app/generics/post",ImmutableMap.of(1,"hello")),is(ImmutableList.of(new MyEntity())));
		assertThat(genericsClientSync.put("http://localhost:8080/rest-app/generics/put",ImmutableMap.of(1,"hello")),is(ImmutableList.of(new MyEntity())));
		assertThat(genericsClientSync.delete("http://localhost:8080/rest-app/generics/delete").get(0),is(new MyEntity()));
	}
	
	/**
	 * More complex with Spring REST Template Based NIORestTemplate
	 *
	 */
	
	@Test
	public void testCRUDSpring() throws InterruptedException, ExecutionException, RestClientException, URISyntaxException{
		
		
		assertThat(rest.getForEntity(new URI("http://localhost:8080/rest-app/rest/get"),List.class).get().getBody().get(0),is("ok"));
		
		assertThat(rest.postForEntity("http://localhost:8080/rest-app/rest/post", new HttpEntity(ImmutableMap.of(1,"hello")), ImmutableSet.class).get().getBody(),is(ImmutableSet.of("hello")));
		assertThat( rest.put("http://localhost:8080/rest-app/rest/put",new HttpEntity(ImmutableMap.of(1,"hello")),ImmutableSet.class).get()
									,is(nullValue()));
		assertThat(rest.delete("http://localhost:8080/rest-app/rest/delete").get(),is(nullValue()));
	}
	
	@Test
	public void testCRUDGenericsSpring() throws InterruptedException, ExecutionException{
		
		
		assertThat(rest.exchange("http://localhost:8080/rest-app/generics/get",HttpMethod.GET,null,new ParameterizedTypeReference<ImmutableList<MyEntity>>(){})
				.get().getBody().get(0),is(new MyEntity()));
		
		assertThat(rest.exchange("http://localhost:8080/rest-app/generics/post",HttpMethod.POST,new HttpEntity(ImmutableMap.of(1,"hello")),new ParameterizedTypeReference<ImmutableList<MyEntity>>(){})
				.get().getBody(),is(ImmutableList.of(new MyEntity())));
		
		assertThat(rest.exchange("http://localhost:8080/rest-app/generics/put",HttpMethod.PUT,new HttpEntity(ImmutableMap.of(1,"hello")),new ParameterizedTypeReference<ImmutableList<MyEntity>>(){})
				.get().getBody(),is(ImmutableList.of(new MyEntity())));
		
		assertThat(rest.exchange("http://localhost:8080/rest-app/generics/delete",HttpMethod.DELETE,null,new ParameterizedTypeReference<ImmutableList<MyEntity>>(){})
				.get().getBody().get(0),is(new MyEntity()));
		
	}
	
	
	
}
