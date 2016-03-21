package com.aol.micro.server.s3;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

public class S3Plugin implements Plugin{
	
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(S3Configuration.class, S3ClientProvider.class, S3Utils.class, S3TransferManagerProvider.class);
	}

}
