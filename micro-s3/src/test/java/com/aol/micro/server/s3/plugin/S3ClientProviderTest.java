package com.aol.micro.server.s3.plugin;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=S3ClientProvider.class, loader = AnnotationConfigContextLoader.class)
public class S3ClientProviderTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    @SneakyThrows
    public void getClient() {
        AmazonS3Client client = ctx.getBean(AmazonS3Client.class);
        assertNotNull(client);
    }
}