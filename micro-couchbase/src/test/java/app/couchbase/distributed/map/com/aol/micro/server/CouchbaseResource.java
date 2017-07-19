package app.couchbase.distributed.map.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.micro.server.distributed.DistributedCache;
import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.distributed.DistributedMap;

@Path("/couchbase")
@Rest
public class CouchbaseResource {

    private final DistributedCache client;

    @Autowired
    public CouchbaseResource(DistributedCache client) {
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
