package app.s3.distributed.map.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.distributed.DistributedMap;

@Path("/s3")
@Rest
public class S3Resource {

    private final DistributedMap client;

    @Autowired
    public S3Resource(DistributedMap client) {
        this.client = client;
    }

    @GET
    @Path("/get")
    public String bucket() {
        return client.get("hello")
                     .toString();
    }

    @GET
    @Path("/put")
    public String put() {
        client.put("hello", "world");
        return "added";
    }
}
