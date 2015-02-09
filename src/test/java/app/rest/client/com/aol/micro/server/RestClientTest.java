package app.rest.client.com.aol.micro.server;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.rest.JacksonUtil;
import com.aol.micro.server.rest.client.JaxRsNIOClient;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

@Configuration
@ComponentScan(basePackages = { "app.rest.client.com.aol.micro.server" })
public class RestClientTest {

   	private final JaxRsNIOClient<List<String>> listClient = new JaxRsNIOClient(1000,1000).withResponse(List.class);
	private final JaxRsNIOClient<ImmutableSet<String>> setClient = new JaxRsNIOClient(1000,1000).withResponse(ImmutableSet.class);;
	private final JaxRsNIOClient<ImmutableList<MyEntity>> genericsClient = new JaxRsNIOClient(1000,1000).withGenericResponse(JacksonUtil.getMapper().getTypeFactory()
																			.constructCollectionType(ImmutableList.class, MyEntity.class));
	
	MicroServerStartup server;
	@Before
	public void startServer(){
		
		server = new MicroServerStartup( RestClientTest.class, ()-> "rest-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
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
	
	
	
}
