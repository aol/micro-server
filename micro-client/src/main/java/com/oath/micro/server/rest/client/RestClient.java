package com.oath.micro.server.rest.client;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Wither;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;


import com.oath.micro.server.rest.jackson.JacksonFeature;
import com.oath.micro.server.rest.jackson.JacksonUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;


@Builder
@AllArgsConstructor
public class RestClient<T> {

    @Wither
	private final Client client;
    @Wither
	private final String contentType;
    @Wither
	private final String accept;
	private final Class<T> response;
    @Wither
	private final JavaType genericResponse;

	/**
	 * Create a new rest client.
	 * @param readTimeoutMillis Read timeout, in milliseconds
	 * @param connectTimeoutMillis Connect timeout, in milliseconds
	 */
	public RestClient(int readTimeoutMillis, int connectTimeoutMillis) {

		this.client = initClient(readTimeoutMillis, connectTimeoutMillis);
		contentType = MediaType.APPLICATION_JSON;
		accept = MediaType.APPLICATION_JSON;
		response = (Class<T>) String.class;
		genericResponse = null;
	}

	public <R> RestClient<R> withResponse(Class<R> response) {
		return new RestClient<R>(client, contentType, accept, response,null);
	}
	public <R> RestClient<R> withGenericResponse(Class<R> responseClass, Class... genericResponse) {
		return new RestClient<R>(client, contentType, accept, null,JacksonUtil.getMapper().getTypeFactory().constructParametricType(responseClass,genericResponse));
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

	public T get(final String url) {
		
		
		final WebTarget webResource = client.target(url);
		
		String s = webResource.request(accept).accept(accept)
				.get(String.class);
		
										

		return  buildResponse(s);

		
				
		
	}
	private T buildResponse(
			String complete) {
		if(normalNotGenericResponseType()){
			if (shouldJustReturnString()){
				return (T)complete;
			}
			else{
				return convertToStandardEntity( complete);
			}
		} else
			return convertToGenericEntity(complete);
	}

	private T convertToGenericEntity(
			String complete) {
		return (T) JacksonUtil.convertFromJson(
				complete, genericResponse);
	}

	private T convertToStandardEntity(
			String complete) {
		return (T) JacksonUtil.convertFromJson(
				complete, response);
	}

	

	private boolean shouldJustReturnString() {
		return String.class.equals(response);
	}

	private boolean normalNotGenericResponseType() {
		return this.genericResponse==null;
	}

	public <V> T post(final String queryResourceUrl,
			final V request) {
	
		
		final WebTarget webResource = client.target(queryResourceUrl);

		return buildResponse(webResource
				.request(accept)
				.accept(accept)
			     .post(Entity.entity(request, contentType),String.class));
		

	}

	public <V> T put(final String queryResourceUrl,
			final V request) {

		CompletableFuture<T> result = new CompletableFuture<>();
		final WebTarget webResource = client.target(queryResourceUrl);

		return buildResponse(webResource
								.request(accept)
								.accept(accept)
								.put(Entity.entity(request, contentType),String.class));

	}
	public T delete(final String queryResourceUrl) {

		CompletableFuture<T> result = new CompletableFuture<>();
		final WebTarget webResource = client.target(queryResourceUrl);

		 return buildResponse(webResource
				.request(accept)
				.accept(accept)
				.delete(String.class));
		

	}
}
