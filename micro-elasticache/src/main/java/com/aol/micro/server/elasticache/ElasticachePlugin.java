package com.aol.micro.server.elasticache;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

/**
 * Created by gordonmorrow on 5/3/17.
 */
public class ElasticachePlugin implements Plugin {

       public PSetX<Class> springClasses() {
            return PSetX.of(ConfigureElasticache.class);
        }
}
