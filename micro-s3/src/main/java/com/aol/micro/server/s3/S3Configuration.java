package com.aol.micro.server.s3;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Getter
public class S3Configuration {

    private final String accessKey;
    private final String secretKey;
    private final String sessionToken;
    private final String region;
    private final boolean defaultChainEnabled;
    private final int uploadThreads;
    private final String uploadThreadNamePrefix;
    private final int maxConnections;

    @Autowired
    public S3Configuration(@Value("${s3.accessKey:#{null}}") String accessKey,
                           @Value("${s3.secretKey:#{null}}") String secretKey,
                           @Value("${s3.useDefaultChain:false}") boolean defaultChainEnabled,
                           @Value("${s3.sessionToken:#{null}}") String sessionToken,
                           @Value("${s3.region:#{null}}") String region,
                           @Value("${s3.upload.threads:5}") int uploadThreads,
                           @Value("${s3.upload.thread.name.prefix:s3-transfer-manager-worker-}") String uploadThreadNamePrefix,
                           @Value("${s3.client.maxConnections:100}") int maxConnections) {

        if((Objects.isNull(accessKey) || Objects.isNull(secretKey)) && !defaultChainEnabled) {
            throw new RuntimeException("No S3 authorization method provided. "
                                       + "Please either enable the aws default credentials chain with s3.useDefaultChain, "
                                       + "or provide access keys via s3.accessKey and s3.secretKey (and optionally s3.sessionToken)");
        }
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.sessionToken = sessionToken;
        this.defaultChainEnabled = defaultChainEnabled;
        this.region = region;
        this.uploadThreads = uploadThreads;
        this.uploadThreadNamePrefix = uploadThreadNamePrefix;
        this.maxConnections = maxConnections;
    }
}
