package com.aol.micro.server.s3.plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.aol.micro.server.s3.S3Configuration;

@Configuration
public class S3TransferManagerProvider {

	@Autowired
	private S3Configuration s3Configuration;

	@Autowired
	private AmazonS3Client amazonS3Client;

	@Bean
	public TransferManager getTransferManager() {
		return new TransferManager(
									amazonS3Client, s3UploadExecutorService());
	}

	@Bean
	public ExecutorService s3UploadExecutorService() {
		ThreadFactory threadFactory = new ThreadFactory() {
			private int threadCount = 1;

			public Thread newThread(Runnable r) {
				Thread thread = new Thread(
											r);
				thread.setName(s3Configuration.getUploadThreadNamePrefix() + threadCount++);
				return thread;
			}
		};
		return Executors.newFixedThreadPool(s3Configuration.getUploadThreads(), threadFactory);
	}

}
