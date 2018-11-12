package com.oath.micro.server;

import com.oath.cyclops.util.ExceptionSoftener;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.Module;
import cyclops.reactive.ReactiveSeq;

public class MicroserverPlugins {

    private final Class[] classes;
    public MicroserverPlugins(Class... classes){
        this.classes = ReactiveSeq.of(classes)
            .appendStream(ReactiveSeq.of(new MicroserverApp(extractClass(),()->"").classes))
            .toArray(i->new Class[i]);
    }
    public MicroserverPlugins(Module mod, Class... classes){
        this.classes = ReactiveSeq.of(classes)
                                  .appendStream(ReactiveSeq.of(new MicroserverApp(extractClass(),mod).classes))
                                  .toArray(i->new Class[i]);
    }
    public Class[] classes(){
        return this.classes;
    }
    private Class extractClass() {
        try {
            return Class.forName(new Exception().getStackTrace()[2].getClassName());
        } catch (ClassNotFoundException e) {
            throw ExceptionSoftener.throwSoftenedException(e);
        }

    }
}
