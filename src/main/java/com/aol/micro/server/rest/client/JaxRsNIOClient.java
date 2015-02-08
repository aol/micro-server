package com.aol.micro.server.rest.client;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import lombok.AllArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.aol.micro.server.rest.JacksonUtil;
import com.aol.micro.server.rest.jersey.JacksonFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Wither
@Builder
@AllArgsConstructor
public class JaxRsNIOClient<T> {

	

	
	private final Client client;
	private final String contentType;
	private final String accept;
	private final Class<T> response;
	

	public JaxRsNIOClient(int readTimeout,int connectTimeout) {
		
		this.client =  initClient(readTimeout,connectTimeout);
		contentType= MediaType.APPLICATION_JSON;
		accept = MediaType.APPLICATION_JSON;
		response = (Class<T>)String.class;
		
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
	
	public CompletableFuture<T> get(final String url) {
		CompletableFuture<T> result = new CompletableFuture();
		 client.target(url).request(accept).accept(accept)
				.async().get(new InvocationCallback<String>() {
                    @Override
                    public void completed(String complete) {
                    	if(String.class.equals(response))
                    		result.complete((T)complete);
                    	else
                    		result.complete((T)JacksonUtil.convertFromJson(complete, response));
                    }

                    @Override
                    public void failed(Throwable ex) {
                       result.completeExceptionally(ex);
                    }
                });
		 return result;

	}

	public <T,V> CompletableFuture<T> post(final String queryResourceUrl, final V request) {

		
		CompletableFuture<T> result = new CompletableFuture();
		final WebTarget webResource = client.target(queryResourceUrl);
		
		
		 webResource.request(accept).accept(accept).async()
				.post(Entity.entity(request, contentType),new InvocationCallback<String>() {
                    @Override
                    public void completed(String complete) {
                       result.complete((T)JacksonUtil.convertFromJson(complete, response));
                    }

                    @Override
                    public void failed(Throwable ex) {
                       result.completeExceptionally(ex);
                    }
                });
		return result;

	}
}
