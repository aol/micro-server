package com.aol.micro.server.spring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.oath.cyclops.types.persistent.PersistentSet;
import com.oath.cyclops.util.ExceptionSoftener;
import cyclops.reactive.ReactiveSeq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


import com.aol.micro.server.InternalErrorCode;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.Microserver;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

@AllArgsConstructor
public class SpringContextFactory {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PersistentSet<Class> classes;
    private final Config config;
    @Wither
    private final SpringBuilder springBuilder;

    public SpringContextFactory(Config config, Class<?> c, Set<Class<?>> classes) {
        PersistentSet<Class> s = config.getClasses()
                        .plusAll(classes);


        s= s.plus(c);
        Microserver microserver = c.getAnnotation(Microserver.class);
        final PersistentSet<Class> immutableS = s;

        s = Optional.ofNullable(microserver)
                    .flatMap(ms -> Optional.ofNullable(ms.blacklistedClasses()))
                    .map(bl -> {
                        Set<Class> blacklistedClasses = Arrays.stream(bl)
                                                              .collect(Collectors.toSet());
                        return (PersistentSet<Class>)immutableS.stream()
                                         .filter(clazz -> !blacklistedClasses.contains(clazz)).hashSet();
                    })
                    .orElse(immutableS);

        this.classes = s;
        this.config = config;

        springBuilder = ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                                            .stream())
                                   .filter(m -> m.springBuilder() != null)
                                   .map(Plugin::springBuilder)
                                   .findFirst()
                                   .orElse(new SpringApplicationConfigurator());
    }

    public SpringContextFactory(SpringBuilder builder, Config config, Class<?> c, Set<Class<?>> classes) {
        PersistentSet<Class> s = config.getClasses();
        for(Class next : classes){
            s = s.plus(next);
        }

        s = s.plus(c);
        Microserver microserver = c.getAnnotation(Microserver.class);
        final PersistentSet<Class> immutableS = s;

        s = Optional.ofNullable(microserver)
                    .flatMap(ms -> Optional.ofNullable(ms.blacklistedClasses()))
                    .map(bl -> {
                        Set<Class> blacklistedClasses = Arrays.stream(bl)
                                                              .collect(Collectors.toSet());
                        PersistentSet<Class> rs = immutableS.stream()
                                         .filter(clazz -> !blacklistedClasses.contains(clazz))
                                            .hashSet();
                        return rs;
                    })
                    .orElse(immutableS);

        this.classes = s;
        this.config = config;

        springBuilder = builder;

    }

    public ApplicationContext createSpringContext() {
        try {
            ApplicationContext springContext = springBuilder.createSpringApp(config, classes.stream().toArray(i->new Class[classes.size()]));
            return springContext;
        } catch (Exception e) {
            logger.error(InternalErrorCode.STARTUP_FAILED_SPRING_INITIALISATION.toString(), e.getMessage());
            ExceptionSoftener.throwSoftenedException(e);
        }
        return null;
    }

}
