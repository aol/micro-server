package com.aol.micro.server.s3.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.Date;

import com.amazonaws.services.s3.AmazonS3Client;
import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.control.Try;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3Reader {

    private final S3Utils utils;
    private final AmazonS3Client client;
    private final String bucket;

    public Date getLastModified(String key) {
        return this.client.getObjectMetadata(bucket, key)
                          .getLastModified();
    }

    /**
     * 
     * Read data from defined S3 bucket with provided key to a String
     * 
     * @param key
     *            To read
     * @return Data as String
     */
    public Try<String, Throwable> getAsString(String key) {
        return Try.withCatch(() -> ReactiveSeq.fromStream(new BufferedReader(
                                                                             new InputStreamReader(
                                                                                                   utils.getInputStream(bucket,
                                                                                                                        key))).lines())
                                              .join("\n"));

    }

    public <T> Try<T, Throwable> getAsObject(String key) {
        return Try.withCatch(() -> {
            ObjectInputStream ois = new ObjectInputStream(
                                                          utils.getInputStream(bucket, key));
            return (T) ois.readObject();
        });

    }
}
