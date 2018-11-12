package com.oath.micro.server.spring;

import java.util.List;

import cyclops.companion.Streams;
import cyclops.data.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import com.oath.micro.server.Plugin;
import com.oath.micro.server.PluginLoader;
import com.oath.micro.server.config.Config;
import com.oath.micro.server.config.ConfigAccessor;

public class SpringApplicationConfigurator implements SpringBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ConfigurableApplicationContext createSpringApp(Config config, Class... classes) {

        logger.debug("Configuring Spring");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.setAllowCircularReferences(config.isAllowCircularReferences());
        rootContext.register(classes);

        rootContext.scan(config.getBasePackages());
        rootContext.refresh();
        logger.debug("Configuring Additional Spring Beans");
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) rootContext).getBeanFactory();

        config.getDataSources()
              .stream()
                .map(Tuple2::_1)
              .filter(it -> !new ConfigAccessor().get()
                                                 .getDefaultDataSourceName()
                                                 .equals(it))
              .forEach(name -> {

                  List<SpringDBConfig> dbConfig = getConfig(config, rootContext, beanFactory);
                  dbConfig.forEach(spring -> spring.createSpringApp(name));

              });
        logger.debug("Finished Configuring Spring");

        return rootContext;
    }

    @Override
    public Class[] classes(Config config, Class... classes) {
        return classes;
    }

    private List<SpringDBConfig> getConfig(Config config, AnnotationConfigWebApplicationContext rootContext,
            ConfigurableListableBeanFactory beanFactory) {
        List<SpringDBConfig> result = ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                                                          .stream())
                                                 .filter(module -> module.springDbConfigurer() != null)
                                                 .map(Plugin::springDbConfigurer)
                                                 .flatMap(Streams::optionalToStream)
                                                 .toList();
        result.forEach(next -> {

            next.setBeanFactory(beanFactory);
            next.setRootContext(rootContext);

            next.setConfig(config);

        });
        return result;

    }

}
