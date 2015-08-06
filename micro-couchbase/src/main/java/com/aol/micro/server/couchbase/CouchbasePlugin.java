package com.aol.micro.server.couchbase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;

public class CouchbasePlugin implements Plugin {

	public Set<Class> springClasses(){
		return new HashSet<>(Arrays.asList(ConfigureCouchbase.class,
				SimpleCouchbaseClient.class));
	}
}
