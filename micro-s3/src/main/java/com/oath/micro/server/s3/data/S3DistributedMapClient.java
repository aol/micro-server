package com.oath.micro.server.s3.data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oath.micro.server.distributed.DistributedMap;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class S3DistributedMapClient<T> implements DistributedMap<T> {

    private final S3Reader reader;
    private final S3ObjectWriter writer;
    private final S3Deleter deleter;

    @Autowired
    public S3DistributedMapClient(@Value("${s3.distributed.map.bucket:}") String bucket, S3Utils utils) {
        this.reader = utils.reader(bucket);
        this.writer = utils.writer(bucket);
        this.deleter = utils.deleter(bucket);
    }

    @Override
    public boolean put(String key, T value) {
        return writer.put(key, value)
                     .isSuccess();
    }

    @Override
    public Optional<T> get(String key) {
        return reader.<T> getAsObject(key)
                     .toOptional();
    }

    @Override
    public void delete(String key) {
        deleter.delete(key);

    }

}
