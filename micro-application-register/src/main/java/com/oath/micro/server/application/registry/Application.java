package com.oath.micro.server.application.registry;

import java.util.Iterator;
import java.util.List;

import com.oath.cyclops.types.persistent.PersistentList;
import cyclops.data.Seq;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


import com.oath.micro.server.rest.jackson.JacksonUtil;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Application implements Iterable<RegisterEntry> {

    PersistentList<RegisterEntry> entries;

    public Application(final List<RegisterEntry> entries) {
        this.entries = Seq.fromIterable(entries);
    }

    @Override
    public Iterator<RegisterEntry> iterator() {
        return entries.iterator();
    }

    public String toString() {
        return JacksonUtil.serializeToJson(entries);
    }


}
