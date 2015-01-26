package com.aol.micro.server;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import lombok.Getter;
import nonautoscan.com.aol.micro.server.AopConfig;
import nonautoscan.com.aol.micro.server.MiscellaneousConfig;
import nonautoscan.com.aol.micro.server.PropertyFileConfig;
import nonautoscan.com.aol.micro.server.ScheduleAndAsyncConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.aol.micro.server.rest.RestResource;
import com.aol.micro.server.servers.ApplicationRegister;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.ServerRunner;
import com.aol.micro.server.servers.SpringApplicationCreator;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class MicroServerStartup {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<Module> modules;
	private final List<Class> classes = Lists.newArrayList(PropertyFileConfig.class,
			MiscellaneousConfig.class, AopConfig.class); //ComponentScanConfig.class,

	@Getter
	private final AnnotationConfigWebApplicationContext springContext;

	public MicroServerStartup(Class c, Module... modules) {
		classes.add(c);
		this.modules = Lists.newArrayList(modules);
		springContext = createSpringContext();

	}

	
	public MicroServerStartup(Module... modules) {
		classes.add(ScheduleAndAsyncConfig.class);
		this.modules = Lists.newArrayList(modules);
		springContext = createSpringContext();
	}

	public MicroServerStartup(List<Class> additionalClasses, Module... modules) {
		this.modules = Lists.newArrayList(modules);
		classes.addAll(additionalClasses);
		springContext = createSpringContext();
	}

	


	public void start() {

		List<ServerApplication> apps = modules.stream().map(module -> 
						createApp(springContext, module)).collect(Collectors.toList());

		ServerRunner runner;
		try{
			
			runner = new ServerRunner(springContext.getBean(ApplicationRegister.class), apps);
		}catch(BeansException e){
			runner = new ServerRunner(apps);
		}
		
		runner.run();
	}

	public AnnotationConfigWebApplicationContext createSpringContext() {
		try {
			AnnotationConfigWebApplicationContext springContext = SpringApplicationCreator.createSpringApp(classes.toArray(new Class[0]));
			return springContext;
		} catch (Exception e) {
			logger.error( ErrorCode.STARTUP_FAILED_SPRING_INITIALISATION.toString(),e.getMessage());
			throw new RuntimeException(e);
		}
		
	}

	private ServerApplication createApp(AnnotationConfigWebApplicationContext rootContext, Module module) {
		ImmutableList<RestResource> resources = SpringApplicationCreator.createJerseyApp(rootContext, module);

		Environment environment = rootContext.getBean(Environment.class);

		environment.assureModule(module);
		String fullRestResource = "/" + module.getContext() + "/*";

		List<FilterData> filterDataList = createFilteredDataList ( module.getFilters() );
		List<ServletData> servletDataList = createServletDataList ( module.getServlets() );

		ServerApplication app = new ServerApplication(new ServerData(environment.getModuleBean(module).getPort(), 
				filterDataList, servletDataList,resources,
				rootContext, fullRestResource, module));
		return app;
	}

	private List<FilterData> createFilteredDataList(Map<String,Filter> filterMap) {
		return filterMap.entrySet().stream().map( e -> { 
			return new FilterData(e.getKey(), e.getValue().getClass().getName(), new DelegatingFilterProxy(e.getValue()));
		}).collect(Collectors.toList());
	}
	private List<ServletData> createServletDataList(Map<String,Servlet> servletMap) {
		return servletMap.entrySet().stream().map( e -> { 
			return new ServletData( e.getValue().getClass().getName(), e.getValue().getClass(), e.getKey());
		}).collect(Collectors.toList());
	}
}