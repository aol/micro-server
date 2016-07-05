package app.couchbase.distributed.map.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.couchbase.DistributedMapClient;

@Path("/couchbase")
@Rest
public class CouchbaseResource {

    private final DistributedMapClient client;

    @Autowired
    public CouchbaseResource(DistributedMapClient client) {
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
