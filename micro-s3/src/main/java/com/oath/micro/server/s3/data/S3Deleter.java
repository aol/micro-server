package com.oath.micro.server.s3.data;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3Deleter {
    private final String bucket;
    private final AmazonS3Client client;

    public void delete(String key) {
        client.deleteObject(bucket, key);
    }
}
