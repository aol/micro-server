package com.aol.micro.server.spring;


import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.ConfigAccessor;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.ModuleBean;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.DAOBuilder;
import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.datasource.hibernate.HibernateSessionBuilder;
import com.aol.micro.server.utility.UsefulStaticMethods;

class SpringApplicationConfigurator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ConfigurableApplicationContext createSpringApp(Config config, Class...classes)  {
	
		logger.debug("Configuring Spring");
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setAllowCircularReferences(false);
		rootContext.register(classes);
		
		Optional<Package> basePackage = Stream.of(classes)
				.filter(cl -> cl.getAnnotation(Microserver.class)!=null)
				.map(cl -> cl.getPackage()).findAny();
		basePackage.ifPresent( base->
		rootContext.scan(Stream.of(classes)
				.map(cl -> cl.getAnnotation(Microserver.class))
				.filter(ano -> ano!=null)
				.map(ano -> ((Microserver)ano).basePackages())
				.map(packages -> UsefulStaticMethods.eitherArray(packages,new String[]{base.getName()}))
				.peek(packages -> Stream.of(packages).forEach(it->System.out.println(it)) )
				.findFirst().get()));
			

		
		rootContext.refresh();
		logger.debug("Configuring Additional Spring Beans");
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) rootContext).getBeanFactory();
		beanFactory.registerSingleton(Environment.class.getCanonicalName(), createEnvironment( rootContext));
		beanFactory.registerSingleton(AccessLogLocationBean.class.getCanonicalName(), createAccessLogLocationBean( rootContext));
		
		config.getDataSources().keySet().stream().filter(it -> !new ConfigAccessor().get().getDefaultDataSourceName().equals(it)).forEach(name -> {
			JdbcConfig jdbc = buildJdbcProperties(rootContext, name);
			DataSource dataSource = buildDataSource(name,jdbc);
			SessionFactory sessionFactory = buildSession(name,config,dataSource,jdbc);
			beanFactory.registerSingleton(name+"DataSource", dataSource);
			beanFactory.registerSingleton(name+"SessionFactory",sessionFactory);
			beanFactory.registerSingleton(name+"HibernateTransactionManager", buildTransactionManager(name,config, dataSource,jdbc));
			beanFactory.registerSingleton(name+"DAOProvider", buildDAOProvider(rootContext, sessionFactory));
			
		});
		logger.debug("Finished Configuring Spring");
		
		return rootContext;
	}
	
	

	private DAOProvider buildDAOProvider(
			AnnotationConfigWebApplicationContext rootContext,
			SessionFactory sessionFactory) {
		
		return DAOBuilder.builder().applicationContext(rootContext).factory(sessionFactory).build().daoProvider();
	}



	



	private HibernateTransactionManager buildTransactionManager(String name,Config config,DataSource dataSource, JdbcConfig jdbc) {
		return HibernateSessionBuilder.builder().packages(config.getDataSources().get(name)).dataSource(dataSource).env(jdbc).build().transactionManager();
	}

	private SessionFactory buildSession(String name, Config config,DataSource dataSource, JdbcConfig jdbc) {
		
		return HibernateSessionBuilder.builder().packages(config.getDataSources().get(name)).dataSource(dataSource).env(jdbc).build().sessionFactory();
	}

	private DataSource buildDataSource(String name, JdbcConfig jdbc) {
		return DataSourceBuilder.builder().env(jdbc).build().mainDataSource();
	}


	private JdbcConfig buildJdbcProperties(AnnotationConfigWebApplicationContext rootContext,String name) {
		return JdbcConfig.builder().properties((Properties)rootContext.getBean("propertyFactory")).name(name).build();
	}


	private AccessLogLocationBean createAccessLogLocationBean(
			AnnotationConfigWebApplicationContext rootContext) {
		Properties props = (Properties)rootContext.getBean("propertyFactory");
		String location = Optional.ofNullable((String)props.get("access.log.output")).orElse("./logs/");
		return new AccessLogLocationBean(location);
	}



	private  Environment createEnvironment(
			AnnotationConfigWebApplicationContext rootContext) {
		Properties props = (Properties)rootContext.getBean("propertyFactory");
		Map<String,ModuleBean> moduleDefinitions =  rootContext.getBeansOfType(ModuleBean.class);
		if(moduleDefinitions ==null)
			return new Environment(props);
		return new Environment(props,moduleDefinitions.values());
		
	}



	
}
