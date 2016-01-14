package com.aol.micro.server.s3;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.s3.AmazonS3Client;
import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;

@Microserver(properties = { "s3.accessKey", "", "s3.secretKey", "" })
public class S3RunnerTest {

	MicroserverApp server;

	@Before
	public void startServer() {

		server = new MicroserverApp(() -> "s3");
		server.start();

	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	public void runAppAndBasicTest() {

		AmazonS3Client s3client = server.getSpringContext().getBean(AmazonS3Client.class);
		assertThat(s3client != null, is(true));
		
		S3Configuration s3Configuration = server.getSpringContext().getBean(S3Configuration.class);
		assertThat(s3Configuration.getAccessKey(), is(""));
		assertThat(s3Configuration.getSecretKey(), is(""));
		assertThat(s3Configuration.getSessionToken() == null, is(true));
		assertThat(s3Configuration.getRegion() == null, is(true));

	}

}
