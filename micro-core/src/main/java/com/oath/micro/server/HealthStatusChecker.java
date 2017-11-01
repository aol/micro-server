package com.aol.micro.server;

@FunctionalInterface
public interface HealthStatusChecker {

    boolean isOk();
}
