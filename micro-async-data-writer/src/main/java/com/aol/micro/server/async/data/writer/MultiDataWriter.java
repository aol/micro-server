package com.aol.micro.server.async.data.writer;



import cyclops.async.Future;
import cyclops.collections.mutable.ListX;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiDataWriter<T> implements DataWriter<T> {

    private final ListX<DataWriter<T>> comparators;

    @Override
    public Future<T> loadAndGet() {
        return comparators.map(c -> c.loadAndGet())
                          .reduce((acc, next) -> acc.combine(next, (v1, v2) -> v1))
                          .orElse(Future.ofResult(null));
    }

    @Override
    public Future<Void> saveAndIncrement(T data) {
        return comparators.map(c -> c.saveAndIncrement(data))
                          .reduce((acc, next) -> acc.combine(next, (v1, v2) -> v1))
                          .orElse(Future.ofResult(null));
    }

    @Override
    public Future<Boolean> isOutOfDate() {
        return comparators.map(c -> c.isOutOfDate())
                          .reduce((acc, next) -> acc.combine(next, (v1, v2) -> v1 || v2))
                          .orElse(Future.ofResult(false));
    }
}
