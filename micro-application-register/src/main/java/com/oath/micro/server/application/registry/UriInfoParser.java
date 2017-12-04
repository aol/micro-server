package com.oath.micro.server.application.registry;

import cyclops.reactive.ReactiveSeq;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class UriInfoParser {

    public static Optional<RegisterEntry> toRegisterEntry(UriInfo uriInfo) {
        if (uriInfo.getQueryParameters().isEmpty()) {
            return Optional.empty();
        } else {
            MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();
            RegisterEntry re = RegisterEntry.builder()
                .context(parameters.getFirst("context"))
                .hostname(parameters.getFirst("hostname"))
                .port(toInt(parameters.getFirst("port")))
                .target(parameters.getFirst("target"))
                .externalPort(toInt(parameters.getFirst("externalPort")))
                .module(parameters.getFirst("module"))
                .health(toHealth(parameters.getFirst("health")))
                .build();

            Map<String, String> manifest = ReactiveSeq.fromIterable(parameters.entrySet())
                .filter(e -> e.getKey().startsWith("manifest."))
                .toMap(e -> e.getKey().replace("manifest.", ""),
                    e -> parameters.getFirst(e.getKey()));

            re.getManifest().clear();
            re.getManifest().putAll(manifest);

            return Optional.of(re);
        }
    }

    private static Health toHealth(String health) {
        if (Objects.nonNull(health)) {
            try {
                return Health.valueOf(health);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                    "'" + health + "' is not valid, valid values are " +
                        Arrays.asList(Health.values()));
            }
        }
        return null;
    }

    private static int toInt(String port) {
        if (Objects.isNull(port)) {
            return -1;
        }

        try {
            return Integer.valueOf(port);
        } catch (Exception e) {
            throw new IllegalArgumentException("'" + port + "' is not a valid number.");
        }
    }

}
