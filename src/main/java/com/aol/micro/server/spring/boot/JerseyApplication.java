package com.aol.micro.server.spring.boot;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.hibernate.SessionFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.ModuleBean;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.spring.datasource.hibernate.DAOBuilder;
import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.properties.PropertyFileConfig;

@Configuration
@PropertySource("classpath:spring-boot-microserver.properties")
public class JerseyApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JerseyApplication.class,PropertyFileConfig.class,Environment.class,AccessLogLocationBean.class);
	}

	public SpringApplicationBuilder config(SpringApplicationBuilder builder) {
		return configure(builder);
		
	}
	
	@Bean
	public AccessLogLocationBean createAccessLogLocationBean(
			ApplicationContext rootContext) {
		Properties props = (Properties)rootContext.getBean("propertyFactory");
		String location = Optional.ofNullable((String)props.get("access.log.output")).orElse("./logs/");
		return new AccessLogLocationBean(location);
	}



	@Bean
	public  Environment createEnvironment(
			ApplicationContext rootContext) {
		Properties props = (Properties)rootContext.getBean("propertyFactory");
		Map<String,ModuleBean> moduleDefinitions =  rootContext.getBeansOfType(ModuleBean.class);
		if(moduleDefinitions ==null)
			return new Environment(props);
		return new Environment(props,moduleDefinitions.values());
		
	}
	
	public DAOProvider buildDAOProvider(
			AnnotationConfigWebApplicationContext rootContext,
			SessionFactory sessionFactory) {
		
		return DAOBuilder.builder().applicationContext(rootContext).factory(sessionFactory).build().daoProvider();
	}
	
	@Bean
    public EmbeddedServletContainerFactory servletContainer() {
		
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(9000);
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        //factory.addErrorPages(new ErrorPage(HttpStatus.404, "/notfound.html"));
        return factory;
    }
	
}
