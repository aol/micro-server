package com.oath.micro.server.s3.plugin;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.s3.DirectoryCleaner;
import com.oath.micro.server.s3.S3Configuration;
import com.oath.micro.server.s3.data.S3DistributedMapClient;
import com.oath.micro.server.s3.data.S3Utils;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

public class S3Plugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(S3ManifestComparatorProvider.class, S3DistributedMapClient.class, S3Configuration.class,
                        S3ClientProvider.class, S3Utils.class, S3TransferManagerProvider.class, DirectoryCleaner.class);
    }

}
