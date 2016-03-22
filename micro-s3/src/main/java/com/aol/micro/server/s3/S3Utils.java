package com.aol.micro.server.s3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.aol.cyclops.control.ReactiveSeq;

@Component
public class S3Utils {

	private final AmazonS3Client client;

	@Autowired
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

	public void delete(String bucketName, List<KeyVersion> objects) {
		ReactiveSeq.fromList(objects).grouped(1000).forEach(l -> {
			DeleteObjectsRequest req = new DeleteObjectsRequest(bucketName);
			req.setKeys(l.toList());
			client.deleteObjects(req);
		});
	}

}
