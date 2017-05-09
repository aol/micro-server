package com.aol.micro.server.couchbase;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;

public class CouchbasePlugin implements Plugin {

    public PSetX<Class> springClasses() {
        return PSetX.of(ConfigureCouchbase.class);
    }
}
