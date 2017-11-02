package com.oath.micro.server.async.data.writer;


import cyclops.async.Future;

public interface DataWriter<T> {

    Future<T> loadAndGet();

    Future<Void> saveAndIncrement(T data);

    Future<Boolean> isOutOfDate();

}