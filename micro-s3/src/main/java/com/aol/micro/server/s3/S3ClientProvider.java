package com.aol.micro.server.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
public class S3ClientProvider {

	@Autowired
	private S3Configuration s3Configuration;

	@Bean
	public AmazonS3Client getClient() {

		AWSCredentials credentials;

		if (s3Configuration.getSessionToken() == null) {
			credentials = new BasicAWSCredentials(s3Configuration.getAccessKey(), s3Configuration.getSecretKey());
		} else {
			credentials = new BasicSessionCredentials(s3Configuration.getAccessKey(), s3Configuration.getSecretKey(),
					s3Configuration.getSessionToken());
		}

		return new AmazonS3Client(credentials);
	}
}
