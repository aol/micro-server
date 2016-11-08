package com.aol.micro.server.log.rest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.CommonRestResource;
import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.log.LogTailer;

import lombok.AllArgsConstructor;

@Path("/log-tail")
@Component
public class LogStreamer implements CommonRestResource, SingletonRestResource {

    private final LogTailer tailer;

    public LogStreamer(LogTailer tailer) {
        this.tailer = tailer;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/stream-file")
    public Response streamTarget(@QueryParam("alias") String alias) {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(
                                                   new OutputStreamWriter(

                os));

                LogListener listener = new LogListener(
                                                       writer);

                tailer.tail(listener, alias);

            }
        };
        return Response.ok(stream)
                       .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/stream")
    public Response stream() {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(
                                                   new OutputStreamWriter(

                os));

                LogListener listener = new LogListener(
                                                       writer);

                tailer.tail(listener);

            }
        };
        return Response.ok(stream)
                       .build();
    }

    @AllArgsConstructor
    static class LogListener implements TailerListener {
        private final Writer writer;
        private volatile Tailer tailer;

        @Override
        public void init(Tailer tailer) {

            this.tailer = tailer;
        }

        @Override
        public void fileNotFound() {
            try {
                writer.write("File not found!");
                writer.flush();
            } catch (IOException e) {
                tailer.stop();
            }

        }

        @Override
        public void fileRotated() {
            try {
                writer.flush();
            } catch (IOException e) {
                tailer.stop();
            }
        }

        @Override
        public void handle(String line) {

            try {
                System.out.println("Writing :" + line);
                writer.write(line);
                writer.write("\n");
                // writer.flush();
            } catch (IOException e) {
                tailer.stop();
            }

        }

        @Override
        public void handle(Exception ex) {

            try {
                writer.write("Error " + ex.getMessage());
                writer.write("/n");
                writer.flush();
            } catch (IOException e) {
                tailer.stop();
            }
        }

        private LogListener(Writer writer) {

            this.writer = writer;
            this.tailer = null;
        }

    }

}