package com.aol.micro.server.s3;

import java.util.Iterator;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3ObjectSummaryIterator implements Iterator<S3ObjectSummary> {

	private final AmazonS3Client client;
	private ListObjectsRequest req;
	private Iterator<S3ObjectSummary> iterator;
	private boolean empty = true;
	public S3ObjectSummaryIterator(AmazonS3Client client, ListObjectsRequest req) {
		this.client = client;
		this.req = req;
		updateIterator();
	}
	
	@Override
	public boolean hasNext() {
		if(iterator.hasNext()) {
			return true;
		} else if(!empty){
			updateIterator();
			return iterator.hasNext();
		} else {
			return false;
		}
	}

	private void updateIterator() {
		if(iterator == null || !iterator.hasNext()) {
			ObjectListing listing = client.listObjects(req);
			req = req.withMarker(listing.getNextMarker());
			empty = !listing.isTruncated();
			iterator =  listing.getObjectSummaries().iterator();
		}
	}
		
	@Override
	public S3ObjectSummary next() {
		updateIterator();
		return iterator.next();
	}

}
