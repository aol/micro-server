package com.oath.micro.server.testing;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.oath.micro.server.rest.jackson.JacksonUtil;

public class RestAgent {

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

    public <T> T post(String url, Object payload, Class<T> type) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(url);

        Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        return request
            .post(Entity.entity(JacksonUtil.serializeToJson(payload), MediaType.APPLICATION_JSON),
                type);
    }

    public String post(String url) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(url);

        Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        return request.post(null, String.class);
    }


}
