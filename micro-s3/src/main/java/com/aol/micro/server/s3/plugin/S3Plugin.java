package com.aol.micro.server.s3.plugin;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.s3.DirectoryCleaner;
import com.aol.micro.server.s3.S3Configuration;
import com.aol.micro.server.s3.data.S3DistributedMapClient;
import com.aol.micro.server.s3.data.S3Utils;
import cyclops.collections.immutable.PersistentSetX;

public class S3Plugin implements Plugin {

    @Override
    public PersistentSetX<Class> springClasses() {
        return PersistentSetX.of(S3ManifestComparatorProvider.class, S3DistributedMapClient.class, S3Configuration.class,
                        S3ClientProvider.class, S3Utils.class, S3TransferManagerProvider.class, DirectoryCleaner.class);
    }

}
