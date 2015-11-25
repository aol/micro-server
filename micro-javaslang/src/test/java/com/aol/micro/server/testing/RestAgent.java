package com.aol.micro.server.testing;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class RestAgent {

	
	public String getJson(String url) {

		Client client = ClientBuilder.newClient();
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(JacksonUtil.getMapper());
		client.register(provider);
		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		return request.get(String.class);

	}
	
	public String get(String url) {

		Client client = ClientBuilder.newClient();
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(JacksonUtil.getMapper());
		client.register(provider);
		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.TEXT_PLAIN);

		return request.get(String.class);

	}

	public<T> T post(String url, Object payload,Class<T> type) {
		Client client = ClientBuilder.newClient();
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(JacksonUtil.getMapper());
		client.register(provider);
		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		String json = JacksonUtil.serializeToJson(payload);
		//return request.post(Entity.entity(json,MediaType.APPLICATION_JSON), type);
		String str = request.post(Entity.entity(json,MediaType.APPLICATION_JSON), String.class);
		return JacksonUtil.convertFromJson(str, type);
	}
	

}
