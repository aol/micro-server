package com.aol.micro.server.async.data.writer;

import java.util.concurrent.atomic.AtomicInteger;

import com.aol.micro.server.manifest.ManifestComparator;

import lombok.Getter;
import lombok.Setter;

public class DummyManifestComparator<T> implements ManifestComparator<T> {

    AtomicInteger loadCalled = new AtomicInteger(
                                                 0);
    AtomicInteger outofDateCalled = new AtomicInteger(
                                                      0);
    AtomicInteger cleanCalled = new AtomicInteger(
                                                  0);
    AtomicInteger cleanAllCalled = new AtomicInteger(
                                                     0);

    private boolean outOfDate;
    @Getter
    @Setter
    private volatile T data;

    @Override
    public <R> ManifestComparator<R> withKey(java.lang.String key) {
        return (DummyManifestComparator) this;
    }

    @Override
    public boolean load() {
        loadCalled.incrementAndGet();
        return true;

    }

    @Override
    public void cleanAll() {
        cleanAllCalled.incrementAndGet();

    }

    @Override
    public void clean(int numberToClean) {
        cleanCalled.incrementAndGet();

    }

    @Override
    public void saveAndIncrement(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isOutOfDate() {
        outofDateCalled.incrementAndGet();
        return true;
    }

}
