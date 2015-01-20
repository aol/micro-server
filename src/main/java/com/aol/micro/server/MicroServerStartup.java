package com.aol.micro.server;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nonautoscan.com.aol.micro.server.AopConfig;
import nonautoscan.com.aol.micro.server.ComponentScanConfig;
import nonautoscan.com.aol.micro.server.HibernateConfig;
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
import com.aol.micro.server.servers.QueryIPRetriever;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.ServerRunner;
import com.aol.micro.server.servers.SpringApplicationCreator;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class MicroServerStartup {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<Module> modules;
	private final List<Class> classes = Lists.newArrayList(PropertyFileConfig.class,ComponentScanConfig.class,
			MiscellaneousConfig.class, AopConfig.class);

	@Getter
	private final AnnotationConfigWebApplicationContext springContext;

	public MicroServerStartup(Class<? extends SchedulingConfiguration> c, Module... modules) {
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

		Optional<ApplicationRegister> register = Optional.empty();
		try{
			register = Optional.of(springContext.getBean(ApplicationRegister.class));
		}catch(BeansException e){
			
		}
		ServerRunner runner = new ServerRunner(register, apps);
		runner.run();
	}

	public AnnotationConfigWebApplicationContext createSpringContext() {
		try {
			AnnotationConfigWebApplicationContext springContext = SpringApplicationCreator.createSpringApp(classes.toArray(new Class[0]));
			return springContext;
		} catch (Exception e) {
//			logger.error( ErrorCode.STARTUP_FAILED_SPRING_INITIALISATION,e,e.getMessage());
//			throw new InvalidStateException( ErrorCode.STARTUP_FAILED_SPRING_INITIALISATION,e);
			throw new RuntimeException(e);
		}
		
	}

	private ServerApplication createApp(AnnotationConfigWebApplicationContext rootContext, Module module) {
		ImmutableList<RestResource> resources = SpringApplicationCreator.createJerseyApp(rootContext, module);

		Environment environment = rootContext.getBean(Environment.class);

		String fullRestResource = "/" + module.getContext() + "/*";

		List<FilterData> filterDataList = environment.getModuleBean(module).getMapping().isPresent() ? createFilteredDataList(rootContext,
				environment.getModuleBean(module).getMapping().get()) : null;

		ServerApplication app = new ServerApplication(new ServerData(environment.getModuleBean(module).getPort(), filterDataList, resources,
				rootContext, fullRestResource, module));
		return app;
	}

	private List<FilterData> createFilteredDataList(AnnotationConfigWebApplicationContext rootContext, String fullRestQuery) {
		List<FilterData> filterDataList;
		filterDataList = Lists.newArrayList(new FilterData(fullRestQuery, "queryIPRetriever", new DelegatingFilterProxy(rootContext
				.getBean(QueryIPRetriever.class))));
		return filterDataList;
	}
}