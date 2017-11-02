package com.oath.micro.server.async.data.writer;



import cyclops.async.Future;
import lombok.Getter;
import lombok.Setter;

public class DummyDataWriter implements DataWriter<String> {

    @Getter
    @Setter
    String data;
    @Getter
    @Setter
    int version = 0;
    @Getter
    @Setter
    boolean outofdate = false;

    @Override
    public Future<String> loadAndGet() {
        return Future.ofResult(data);
    }

    @Override
    public Future<Void> saveAndIncrement(String data) {
        this.data = data;
        version++;
        return Future.ofResult(null);
    }

    @Override
    public Future<Boolean> isOutOfDate() {
        return Future.ofResult(outofdate);
    }

}
