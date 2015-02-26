package com.aol.micro.server.spring.boot;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.ModuleBean;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.spring.boot.JerseyApplication;
import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.DAOBuilder;
import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.datasource.hibernate.HibernateSessionBuilder;
import com.aol.micro.server.spring.grizzly.GrizzlyServer;
import com.aol.micro.server.spring.properties.PropertyFileConfig;

class BootApplicationConfigurator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ConfigurableApplicationContext createSpringApp(Config config, Class...classes)  {
		Optional<Package> basePackage = Stream.of(classes)
				.filter(cl -> cl.getAnnotation(Microserver.class)!=null)
				.map(cl -> cl.getPackage()).findAny();
	
		
		List<Class> classList = new ArrayList<Class>();
		classList.addAll(Arrays.asList(classes));
		classList.add(JerseyApplication.class);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(classList.toArray(new Class[0]));
		new JerseyApplication().config(builder);
		return builder.application().run();
	}
	

}
