package com.aol.micro.server.reactive.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.micro.server.rest.jackson.JacksonFeature;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.fasterxml.jackson.databind.JavaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.experimental.Wither;

@AllArgsConstructor
@Builder
@Wither
public class ReactiveRequest {

    private final Client client;
    private final String contentType;
    private final String accept;
    private final String stringFormat;

    /**
     * Create a new rest client.
     * @param readTimeoutMillis Read timeout, in milliseconds
     * @param connectTimeoutMillis Connect timeout, in milliseconds
     */
    public ReactiveRequest(int readTimeoutMillis, int connectTimeoutMillis) {

        this.client = initClient(readTimeoutMillis, connectTimeoutMillis);
        contentType = MediaType.APPLICATION_JSON;
        accept = MediaType.APPLICATION_JSON;
        stringFormat = "UTF-8";

    }

    protected Client initClient(int rt, int ct) {

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, ct);
        clientConfig.property(ClientProperties.READ_TIMEOUT, rt);

        ClientBuilder.newBuilder()
                     .register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(clientConfig);

        return client;

    }

    @SneakyThrows
    public ReactiveSeq<String> getTextStream(final String url) {

        final WebTarget webResource = client.target(url);

        InputStream s = webResource.request(MediaType.TEXT_PLAIN)
                                   .accept(MediaType.TEXT_PLAIN)
                                   .get(InputStream.class);

        BufferedReader reader = new BufferedReader(
                                                   new InputStreamReader(
                                                                         s, this.stringFormat));
        return ReactiveSeq.fromStream(reader.lines());

    }

    @SneakyThrows
    public ReactiveSeq<String> getStream(final String url) {

        final WebTarget webResource = client.target(url);

        InputStream s = webResource.request(accept)
                                   .accept(accept)
                                   .get(InputStream.class);

        BufferedReader reader = new BufferedReader(
                                                   new InputStreamReader(
                                                                         s, this.stringFormat));
        return ReactiveSeq.fromStream(reader.lines());

    }

    @SneakyThrows
    public <V> ReactiveSeq<String> postStream(final String url, final V request) {

        final WebTarget webResource = client.target(url);

        InputStream s = webResource.request(accept)
                                   .accept(accept)
                                   .post(Entity.entity(request, contentType), InputStream.class);

        BufferedReader reader = new BufferedReader(
                                                   new InputStreamReader(
                                                                         s, this.stringFormat));
        return ReactiveSeq.fromStream(reader.lines());

    }

    @SneakyThrows
    public <V> ReactiveSeq<String> putStream(final String url, final V request) {

        final WebTarget webResource = client.target(url);

        InputStream s = webResource.request(accept)
                                   .accept(accept)
                                   .put(Entity.entity(request, contentType), InputStream.class);

        BufferedReader reader = new BufferedReader(
                                                   new InputStreamReader(
                                                                         s, this.stringFormat));
        return ReactiveSeq.fromStream(reader.lines());

    }

    @SneakyThrows
    public <V> ReactiveSeq<String> deleteStream(final String url, final V request) {

        final WebTarget webResource = client.target(url);

        InputStream s = webResource.request(accept)
                                   .accept(accept)
                                   .delete(InputStream.class);

        BufferedReader reader = new BufferedReader(
                                                   new InputStreamReader(
                                                                         s, this.stringFormat));
        return ReactiveSeq.fromStream(reader.lines());

    }

    public <T> ReactiveSeq<T> getJsonStream(final String url, Class<T> type) {

        return getStream(url).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

    public <T, V> ReactiveSeq<T> getJsonStream(final String url, JavaType type) {

        return getStream(url).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

    public <T> ReactiveSeq<T> deleteJsonStream(final String url, Class<T> type) {

        return getStream(url).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

    public <T, V> ReactiveSeq<T> deleteJsonStream(final String url, JavaType type) {

        return getStream(url).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

    public <T, V> ReactiveSeq<T> postJsonStream(final String url, final V request, Class<T> type) {

        return postStream(url, request).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

    public <T, V> ReactiveSeq<T> postJsonStream(final String url, final V request, JavaType type) {

        return postStream(url, request).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

    public <T, V> ReactiveSeq<T> putJsonStream(final String url, final V request, Class<T> type) {

        return putStream(url, request).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

    public <T, V> ReactiveSeq<T> putJsonStream(final String url, final V request, JavaType type) {

        return putStream(url, request).map(jsonString -> JacksonUtil.convertFromJson(jsonString, type));

    }

}
