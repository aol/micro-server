package com.oath.micro.server.testing;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RestAgent {

    public String get(String url) {

        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(url);

        Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        return request.get(String.class);

    }

}
