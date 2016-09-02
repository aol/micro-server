package com.aol.micro.server;

@FunctionalInterface
public interface HealthStatus {

    boolean isOk();
}
