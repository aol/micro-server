package com.aol.micro.server.s3.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.aol.cyclops.control.FutureW;
import com.aol.cyclops.control.Try;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3StringWriter {

	private final AmazonS3Client client;
	private final String bucket;
	private final ExecutorService uploadService;

	/**
	 * 
	 * Read data from defined S3 bucket with provided key to a String. Calling
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
			ObjectMetadata md = new ObjectMetadata();
			md.setContentLength(bytes.length);
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
	public FutureW<PutObjectResult> putAsync(String key, String value) {
		return FutureW	.ofSupplier(() -> put(key, value), this.uploadService)
						.map(Try::get);
	}

}
