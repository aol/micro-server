package com.aol.micro.server.async.data.writer;

import com.aol.cyclops.control.FutureW;
import com.aol.cyclops.data.collections.extensions.standard.ListX;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiDataWriter<T> implements DataWriter<T> {

	private final ListX<DataWriter<T>> comparators;

	@Override
	public FutureW<T> loadAndGet() {
		return comparators	.map(c -> c.loadAndGet())
							.reduce((acc, next) -> acc.ap(next, (v1, v2) -> v1))
							.orElse(FutureW.ofResult(null));
	}

	@Override
	public FutureW<Void> saveAndIncrement(T data) {
		return comparators	.map(c -> c.saveAndIncrement(data))
							.reduce((acc, next) -> acc.ap(next, (v1, v2) -> v1))
							.orElse(FutureW.ofResult(null));
	}

	@Override
	public FutureW<Boolean> isOutOfDate() {
		return comparators	.map(c -> c.isOutOfDate())
							.reduce((acc, next) -> acc.ap(next, (v1, v2) -> v1 || v2))
							.orElse(FutureW.ofResult(false));
	}
}
