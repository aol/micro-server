package com.aol.micro.server.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3Configuration {

	private AWSCredentials credentials;

	public S3Configuration(@Value("${s3.accessKey}") String accessKey, @Value("${s3.secretKey}") String secretKey, 
			@Value("s3.sessionToken:#null") String sessionToken) {
		if (sessionToken == null) {
			credentials = new BasicAWSCredentials(accessKey, secretKey);
		} else {
			credentials = new BasicSessionCredentials(accessKey, secretKey, sessionToken);
		}
	}

	@Bean
	public AmazonS3Client getClient() {
		return new AmazonS3Client(credentials);
	}
}
