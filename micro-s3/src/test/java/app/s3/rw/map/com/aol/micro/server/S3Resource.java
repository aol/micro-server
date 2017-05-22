package app.s3.rw.map.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import cyclops.control.Try;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.transfer.Upload;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.s3.data.S3Reader;
import com.aol.micro.server.s3.data.S3Utils;
import com.aol.micro.server.s3.data.S3ObjectWriter;

@Path("/s3")
@Rest
public class S3Resource {

    private final S3Reader client;
    private final S3ObjectWriter writer;

    @Autowired
    public S3Resource(S3Utils utils) {
        this.client = utils.reader("aolp-lana-dev-test-partition-us-east-1");
        this.writer = utils.writer("aolp-lana-dev-test-partition-us-east-1");
    }

    @GET
    @Path("/get")
    public String bucket() {
        return client.getAsString("hello")
                     .get();
    }

    @GET
    @Path("/put")
    public String put() {
        Try<Upload, Throwable> operation = writer.put("hello", "world");
        if(operation.isSuccess())
            return "added";
        return operation.failureGet().getMessage();
    }
}
