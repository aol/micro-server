package com.aol.micro.server.s3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;

public class S3Plugin implements Plugin{
	
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(S3Configuration.class));
	}

}
