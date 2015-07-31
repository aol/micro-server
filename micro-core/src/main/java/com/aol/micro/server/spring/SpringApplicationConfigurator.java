package com.aol.micro.server.spring;

import java.util.Optional;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.ConfigAccessor;
import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;

class SpringApplicationConfigurator implements SpringBuilder {
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
			JdbcConfig jdbc = buildJdbcProperties(rootContext, name);
			DataSource dataSource = buildDataSource(name, jdbc);
			Optional<SpringDBConfig> dbConfig = getConfig(config,jdbc,dataSource,rootContext,beanFactory);
			dbConfig.ifPresent(  spring-> spring.createSpringApp(name) );
			beanFactory.registerSingleton(name + "DataSource", dataSource);
			

		});
		logger.debug("Finished Configuring Spring");

		return rootContext;
	}

	
	private Optional<SpringDBConfig> getConfig(Config config,JdbcConfig jdbc, DataSource dataSource, AnnotationConfigWebApplicationContext rootContext, ConfigurableListableBeanFactory beanFactory) {
		SpringDBConfig result = null;
		
			try {

				result = (SpringDBConfig) Class.forName("com.aol.micro.server.spring.SpringConfigurer").newInstance();
				result.setBeanFactory(beanFactory);
				result.setRootContext(rootContext);
				result.setDataSource(dataSource);
				result.setConfig(config);
				result.setJdbc(jdbc);
			} catch (InstantiationException | IllegalAccessException  | ClassNotFoundException e) {
				logger.debug(e.getMessage());
			
			}
		
		return Optional.ofNullable(result);
	}


	private DataSource buildDataSource(String name, JdbcConfig jdbc) {
		return DataSourceBuilder.builder().env(jdbc).build().mainDataSource();
	}

	private JdbcConfig buildJdbcProperties(AnnotationConfigWebApplicationContext rootContext, String name) {
		return JdbcConfig.builder().properties((Properties) rootContext.getBean("propertyFactory")).name(name).build();
	}

}
