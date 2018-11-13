package com.oath.micro.server.module;

import com.oath.micro.server.MicroserverPlugins;
import com.oath.micro.server.plugin.Bean1;
import cyclops.reactive.ReactiveSeq;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MicroserverPluginsTest {

    @Test
    public void test(){
        assertTrue(ReactiveSeq.of(new MicroserverPlugins().classes())
            .contains(Bean1.class));
    }
}
