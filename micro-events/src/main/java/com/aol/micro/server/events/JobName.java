package com.aol.micro.server.events;

import java.util.stream.Stream;

import com.aol.cyclops.control.Maybe;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface JobName {

    public static enum Types {

        SIMPLE(new SimpleJobName()), PACKAGE(new PackageJobName()), FULL(new FullJobName());

        @Getter
        private final JobName creator;

        private Types(JobName job) {
            this.creator = job;
        }

    }

    public String getType(Class c);

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SimpleJobName implements JobName {

        @Override
        public String getType(Class c) {
            return c.getSimpleName();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FullJobName implements JobName {

        @Override
        public String getType(Class c) {
            return c.getCanonicalName();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PackageJobName implements JobName {

        @Override
        public String getType(Class c) {
            return Maybe.ofNullable(c.getPackage())
                        .map(Package::getName)
                        .map(packageName -> packageName.split("\\."))
                        .stream()
                        .flatMap(Stream::of)
                        .takeRight(1)
                        .singleOptional()
                        .map(i -> i + ".")
                        .orElse("")
                    + c.getSimpleName();
        }
    }
}
