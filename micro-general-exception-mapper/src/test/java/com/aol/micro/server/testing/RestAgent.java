package com.aol.micro.server.testing;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aol.micro.server.rest.jackson.JacksonUtil;

public class RestAgent {

	
	public Response get(String url) {

		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(url);

		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		return request.get();

	}
	
	
	

}
