package com.aol.micro.server.s3;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

public class S3UtilsTest {

	private boolean answer = true;

	@Test
	public void getAllSummaries() {
		answer = true;
		AmazonS3Client client = mock(AmazonS3Client.class);
		ObjectListing objectListing = mock(ObjectListing.class);
		when(client.listObjects(any(ListObjectsRequest.class))).thenReturn(objectListing);
		when(objectListing.isTruncated()).thenAnswer(__ -> {
			try {
				return answer;
			} finally {
				answer = false;
			}
		});
		S3Utils utils = new S3Utils(client);
		utils.getAllSummaries(new ListObjectsRequest());
		verify(objectListing, times(2)).getObjectSummaries();
	}

	@Test
	public void getSummariesStream() {
		answer = true;
		AmazonS3Client client = mock(AmazonS3Client.class);
		ObjectListing objectListing = mock(ObjectListing.class);
		when(client.listObjects(any(ListObjectsRequest.class))).thenReturn(objectListing);
		when(objectListing.isTruncated()).thenAnswer(__ -> {
			try {
				return answer;
			} finally {
				answer = false;
			}
		});
		// when(objectListing.getObjectSummaries()).thenReturn(summaries);

		S3Utils utils = new S3Utils(client);
		verify(objectListing, times(0)).getObjectSummaries();
		utils.getSummariesStream(new ListObjectsRequest(), s -> s.getKey());
		verify(objectListing, times(2)).getObjectSummaries();
	}

	@Test
	public void deleteObjects() {
		AmazonS3Client client = mock(AmazonS3Client.class);
		S3Utils utils = new S3Utils(client);
		List<KeyVersion> keys = new ArrayList<>();
		for (int i = 0; i < 2000; i++) {
			keys.add(new KeyVersion(""));
		}

		utils.delete("", keys);
		
		verify(client, times(2)).deleteObjects(any());
	}
}
