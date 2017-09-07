package com.aol.micro.server.application.registry;

import javax.ws.rs.core.UriInfo;
import java.util.Optional;

public class UriInfoParser {

    public static Optional<RegisterEntry> toRegisterEntry(UriInfo uriInfo) {
        if (uriInfo.getQueryParameters().isEmpty()) {
            return Optional.empty();
        } else {
            RegisterEntry re = new RegisterEntry();
            // TODO: add the population logic
            return Optional.of(re);
        }
    }
}
