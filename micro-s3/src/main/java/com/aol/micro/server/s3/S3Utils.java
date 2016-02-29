package com.aol.micro.server.s3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Utils {

	private final AmazonS3Client client;

	public S3Utils(AmazonS3Client client) {
		this.client = client;
	}

	public List<S3ObjectSummary> getAllSummaries(ListObjectsRequest req) {
		List<S3ObjectSummary> result = new ArrayList<>();
		String marker = null;
		ListObjectsRequest req2 = (ListObjectsRequest) req.clone();
		ObjectListing listing;
		do {
			listing = client.listObjects(req2.withMarker(marker));
			marker = listing.getNextMarker();
			result.addAll(listing.getObjectSummaries());
		} while (listing.isTruncated());
		return result;
	}
	/*
	 * TODO implement smarter mechanism to reduce number of queries
	 */
	public <T> Stream<T> getSummariesStream(ListObjectsRequest req, Function<S3ObjectSummary, T> processor) {
		return getAllSummaries(req).stream().map(processor);
	}

}
