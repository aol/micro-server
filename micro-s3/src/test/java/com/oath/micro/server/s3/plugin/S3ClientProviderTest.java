package com.oath.micro.server.s3.plugin;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.oath.micro.server.s3.S3Configuration;
import com.oath.micro.server.s3.plugin.S3ClientProvider;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=S3ClientProvider.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = {
        "s3.accessKey=",
        "s3.secretKey="
})
public class S3ClientProviderTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    @SneakyThrows
    public void getClient() {
        AmazonS3Client client = ctx.getBean(AmazonS3Client.class);
        assertNotNull(client);
    }

    @Test
    public void defaultChain() {
        S3ClientProvider provider = new S3ClientProvider();
        S3Configuration s3Configuration = new S3Configuration(null,
                                                              null,
                                                              true,
                                                              null,
                                                              null,
                                                              5,
                                                              "s3-transfer-manager-worker-",
                                                              100);
        ReflectionTestUtils.setField(provider, "s3Configuration", s3Configuration);
        // system properties used here as they're the easiest to test
        // but it can also pull them from the metadata service on ec2
        System.setProperty("aws.accessKeyId", "fakeKeyId");
        System.setProperty("aws.secretKey", "fakeSecretKey");
        AWSCredentials credentials = provider.getAwsCredentials();
        assertEquals("fakeKeyId", credentials.getAWSAccessKeyId());
        assertEquals("fakeSecretKey", credentials.getAWSSecretKey());
    }

    @Test
    public void accessKey() {
        S3ClientProvider provider = new S3ClientProvider();
        S3Configuration s3Configuration = new S3Configuration("fakeProvidedKeyId",
                                                              "fakeProvidedSecretKey",
                                                              false,
                                                              null,
                                                              null,
                                                              5,
                                                              "s3-transfer-manager-worker-",
                                                              100);
        ReflectionTestUtils.setField(provider, "s3Configuration", s3Configuration);
        AWSCredentials credentials = provider.getAwsCredentials();
        assertEquals("fakeProvidedKeyId", credentials.getAWSAccessKeyId());
        assertEquals("fakeProvidedSecretKey", credentials.getAWSSecretKey());
    }

    @Test
    public void sessionToken() {
        S3ClientProvider provider = new S3ClientProvider();
        S3Configuration s3Configuration = new S3Configuration("fakeProvidedKeyId",
                                                              "fakeProvidedSecretKey",
                                                              false,
                                                              "fakeProvidedSessionToken",
                                                              null,
                                                              5,
                                                              "s3-transfer-manager-worker-",
                                                              100);
        ReflectionTestUtils.setField(provider, "s3Configuration", s3Configuration);
        BasicSessionCredentials credentials = (BasicSessionCredentials) provider.getAwsCredentials();
        assertEquals("fakeProvidedKeyId", credentials.getAWSAccessKeyId());
        assertEquals("fakeProvidedSecretKey", credentials.getAWSSecretKey());
        assertEquals("fakeProvidedSessionToken", credentials.getSessionToken());
    }
}