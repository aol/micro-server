package com.aol.micro.server.s3.plugin;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.s3.data.S3Utils;
import com.aol.micro.server.s3.manifest.comparator.S3ManifestComparator;

@Configuration
public class S3ManifestComparatorProvider {

    @Value("${s3.manifest.comparator.bucket:}")
    private String bucket;
    @Value("${s3.manifest.comparator.key:default}")
    private String key;
    @Autowired
    private S3Utils s3Utils;

    @Bean
    public S3ManifestComparator s3ManifestComparator() throws IOException, URISyntaxException {
        return new S3ManifestComparator(
                                        s3Utils.reader(bucket), s3Utils.writer(bucket), s3Utils.deleter(bucket),
                                        s3Utils.stringWriter(bucket)).withKey(key);
    }
}
