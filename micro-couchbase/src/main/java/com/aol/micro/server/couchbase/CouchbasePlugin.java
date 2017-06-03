package com.aol.micro.server.couchbase;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

public class CouchbasePlugin implements Plugin {

    public PersistentSetX<Class> springClasses() {
        return PersistentSetX.of(ConfigureCouchbase.class);
    }
}
