package com.oath.micro.server.async.data.writer;



import cyclops.control.Future;
import cyclops.reactive.collections.mutable.ListX;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiDataWriter<T> implements DataWriter<T> {

    private final ListX<DataWriter<T>> comparators;

    @Override
    public Future<T> loadAndGet() {
        return comparators.map(c -> c.loadAndGet())
                          .foldLeft((acc, next) -> acc.zip(next, (v1, v2) -> v1))
                          .orElse(Future.ofResult(null));
    }

    @Override
    public Future<Void> saveAndIncrement(T data) {
        return comparators.map(c -> c.saveAndIncrement(data))
                          .foldLeft((acc, next) -> acc.zip(next, (v1, v2) -> v1))
                          .orElse(Future.ofResult(null));
    }

    @Override
    public Future<Boolean> isOutOfDate() {
        return comparators.map(c -> c.isOutOfDate())
                          .foldLeft((acc, next) -> acc.zip(next, (v1, v2) -> v1 || v2))
                          .orElse(Future.ofResult(false));
    }
}
