package com.aol.micro.server.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.cyclops.sequence.SequenceM;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.ConfigAccessor;

public class SpringApplicationConfigurator implements SpringBuilder {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ConfigurableApplicationContext createSpringApp(Config config, Class... classes) {

		logger.debug("Configuring Spring");
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setAllowCircularReferences(config.isAllowCircularReferences());
		rootContext.register(classes);

		rootContext.scan(config.getBasePackages());
		rootContext.refresh();
		logger.debug("Configuring Additional Spring Beans");
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) rootContext).getBeanFactory();
		
		config.getDataSources().keySet().stream().filter(it -> !new ConfigAccessor().get().getDefaultDataSourceName().equals(it)).forEach(name -> {
			
			List<SpringDBConfig> dbConfig = getConfig(config,rootContext,beanFactory);
			dbConfig.forEach(  spring-> spring.createSpringApp(name) );
			
			

		});
		logger.debug("Finished Configuring Spring");

		return rootContext;
	}

	
	private List<SpringDBConfig> getConfig(Config config, AnnotationConfigWebApplicationContext rootContext, ConfigurableListableBeanFactory beanFactory) {
		List<SpringDBConfig> result = 
				SequenceM.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
				.filter(module -> module.springDbConfigurer()!=null)
				.flatMapOptional(Plugin::springDbConfigurer)
				.toList();
		result.forEach( next -> {
			

				
				next.setBeanFactory(beanFactory);
				next.setRootContext(rootContext);
				
				next.setConfig(config);
				
			
			
		});
		return result;
		
		
	}


	

}
