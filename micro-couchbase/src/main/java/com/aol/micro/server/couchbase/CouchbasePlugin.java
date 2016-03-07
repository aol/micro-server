package com.aol.micro.server.couchbase;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

public class CouchbasePlugin implements Plugin {

	public PSetX<Class> springClasses() {
		return PSetX.of(ConfigureCouchbase.class);
	}
}
