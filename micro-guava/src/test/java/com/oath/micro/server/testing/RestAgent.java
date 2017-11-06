package com.oath.micro.server.testing;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.oath.micro.server.rest.jackson.JacksonUtil;
import cyclops.data.DIET;
import cyclops.function.Predicates;
import org.junit.Test;

import static cyclops.data.Range.range;
import static cyclops.matching.Api.Case;
import static cyclops.matching.Api.Match;
import static cyclops.matching.Api.MatchType;

public class RestAgent {
	@Test
	public void diet(){

		DIET<Integer> diet = DIET.cons(DIET.cons(range(1, 5)), range(6, 7), DIET.cons(range(10, 20)));
        DIET<Integer> added = diet.add(range(50, 60));
        DIET<Integer> removed = added.remove(range(2,4));
		//[1 .. 2],[4 .. 5],[6 .. 7],[10 .. 20],[50 .. 60]


	}
	
	public String getJson(String url) {

		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		return request.get(String.class);

	}
	
	public String get(String url) {

		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.TEXT_PLAIN);

		return request.get(String.class);

	}

	public<T> T post(String url, Object payload,Class<T> type) {
		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		return request.post(Entity.entity(JacksonUtil.serializeToJson(payload),MediaType.APPLICATION_JSON), type);
	}
	

}
