package com.oath.micro.server.couchbase;

import com.oath.micro.server.Plugin;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class CouchbasePlugin implements Plugin {

    public Set<Class> springClasses() {
        return SetX.of(ConfigureCouchbase.class);
    }
}
