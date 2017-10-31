package com.aol.micro.server.couchbase;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class CouchbasePlugin implements Plugin {

    public Set<Class> springClasses() {
        return SetX.of(ConfigureCouchbase.class);
    }
}
