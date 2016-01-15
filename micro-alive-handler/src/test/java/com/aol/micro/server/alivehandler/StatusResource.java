package com.aol.micro.server.alivehandler;

import com.aol.micro.server.auto.discovery.Rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Rest
@Path("/status")
public class StatusResource {
    /**
     * GET ping endpoint.
     * @return string
     */
    @GET
    @Path("ping")
    public String ping() {
        return "pong";
    }
}
