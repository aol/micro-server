package com.oath.micro.server;

@FunctionalInterface
public interface HealthStatusChecker {

    boolean isOk();
}
