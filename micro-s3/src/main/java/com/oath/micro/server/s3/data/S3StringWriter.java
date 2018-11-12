package com.oath.micro.server.s3.data;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;

import cyclops.control.Future;
import cyclops.control.Try;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3StringWriter {

    private final AmazonS3Client client;
    private final String bucket;
    private final ExecutorService uploadService;
    private final boolean aes256Encryption;

    S3StringWriter(AmazonS3Client client, String bucket, ExecutorService uploadService){
        this(client, bucket, uploadService, false);
    }

    /**
     * 
     * Writes String data to defined S3 bucket with provided key. Calling
     * map / flatMap on the returned try instance will catch any exceptions, any
     * exceptions thrown will convert a Success to a Failure
     * 
     * This call is blocking.
     * 
     * @param key
     *            To read
     * @return Data as String
     */
    public Try<PutObjectResult, Throwable> put(String key, String value) {

        return Try.withCatch(() -> {

            byte[] bytes = value.getBytes("UTF-8");
            ByteArrayInputStream stream = new ByteArrayInputStream(
                                                                   bytes);
            ObjectMetadata md = createMetadata(bytes.length);
            return client.putObject(bucket, key, stream, md);

        });

    }

    /**
     * Non-blocking call
     * 
     * @param key
     * @param value
     * @return
     */
    public Future<PutObjectResult> putAsync(String key, String value) {
        return Future.of(() -> put(key, value), this.uploadService)
                      .flatMap(t->t.fold(p->Future.ofResult(p),e->Future.ofError(e)));
    }

    /**
     * Metadata object creation
     * @param length
     *
     * @return Metadata with AES_256 encryption if enabled
     */
    private ObjectMetadata createMetadata(int length) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(length);
        if (aes256Encryption)
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        return metadata;
    }

}
