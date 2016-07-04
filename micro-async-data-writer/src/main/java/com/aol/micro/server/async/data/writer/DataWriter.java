package com.aol.micro.server.async.data.writer;

import com.aol.cyclops.control.FutureW;

public interface DataWriter<T> {

	FutureW<T> loadAndGet();

	FutureW<Void> saveAndIncrement(T data);

	FutureW<Boolean> isOutOfDate();

}