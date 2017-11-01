package com.aol.micro.server.reactive.rest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.stream.Stream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.oath.cyclops.util.ExceptionSoftener;
import cyclops.reactive.ReactiveSeq;
import org.reactivestreams.Publisher;


import com.aol.micro.server.rest.jackson.JacksonUtil;

public class ReactiveResponse {

    public static <T> Response streamAsJson(Stream<T> json) {
        return publishAsJson(ReactiveSeq.fromStream(json));
    }

    public static <T> Response publishAsJson(Publisher<T> json) {

        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(
                                                   new OutputStreamWriter(
                                                                          os));

                ReactiveSeq.fromPublisher(json)
                           .map(JacksonUtil::serializeToJson)
                           .forEach(ExceptionSoftener.softenConsumer(json -> {

                    writer.write(json);
                    writer.write("\n");

                }), e -> {
                } , ExceptionSoftener.softenRunnable(() -> writer.flush()));

                writer.flush();
            }
        };
        return Response.ok(stream)
                       .build();
    }

}
