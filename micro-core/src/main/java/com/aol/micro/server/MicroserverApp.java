package com.aol.micro.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.Configurer;
import com.aol.micro.server.config.MicroserverConfigurer;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.servers.ApplicationRegister;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.ServerRunner;
import com.aol.micro.server.servers.model.GrizzlyApplicationFactory;
import com.aol.micro.server.spring.SpringContextFactory;
import com.aol.simple.react.exceptions.ExceptionSoftener;
import com.google.common.collect.Lists;

/**
 * 
 * Startup class for Microserver instance
 * 
 * @author johnmcclean
 *
 */
public class MicroserverApp {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<Module> modules;
	private final CompletableFuture end = new CompletableFuture();
	private final ExceptionSoftener softener = ExceptionSoftener.singleton.factory
			.getInstance();

	@Getter
	private final ApplicationContext springContext;

	/**
	 * This will construct a Spring context for this Microserver instance.
	 * The calling class will be used to determine the base package to auto-scan from for Spring Beans
	 * It will attempt to pick up an @Microservice annotation first, if not present the package of the calling class
	 * will be used.
	 * 
	 * @param modules Multiple Microservice end points that can be deployed within a single Spring context
	 */
	public MicroserverApp(Module... modules) {
		this.modules = Lists.newArrayList(modules);
		springContext = new SpringContextFactory(new MicroserverConfigurer().buildConfig(
				extractClass()), extractClass(),
				modules[0].getSpringConfigurationClasses())
				.createSpringContext();

	}

	/**
	 * This will construct a Spring context for this Microserver instance.
	 * The provided class will be used to determine the base package to auto-scan from for Spring Beans
	 * It will attempt to pick up an @Microservice annotation first, if not present the package of the provided class
	 * will be used.
	 * 
	 * @param c Class used to configure Spring
	 * @param modules Multiple Microservice end points that can be deployed within a single Spring context
	 */
	public MicroserverApp(Class c, Module... modules) {

		this.modules = Lists.newArrayList(modules);
		springContext = new SpringContextFactory(
				new MicroserverConfigurer().buildConfig(c), c,
				modules[0].getSpringConfigurationClasses())
				.createSpringContext();

	}

	/**
	 * This will construct a Spring context for this Microserver instance.
	 * The provided Config object will be used to configure Spring
	 * 
	 * @param config Spring configuration
	 * @param modules Multiple Microservice end points that can be deployed within a single Spring context
	 */
	public MicroserverApp(Config config, Module... modules) {

		this.modules = Lists.newArrayList(modules);
		config.set();
		springContext = new SpringContextFactory(config,
				modules[0].getSpringConfigurationClasses())
				.createSpringContext();

	}
	
	

	private Class extractClass() {
		try {
			return Class.forName(new Exception().getStackTrace()[2]
					.getClassName());
		} catch (ClassNotFoundException e) {
			softener.throwSoftenedException(e);
		}
		return null; // unreachable normally
	}

	

	public void stop() {

		end.complete(true);
		Config.reset();

	}

	public void run() {
		start().forEach(thread -> join(thread));
	}

	public List<Thread> start() {

		List<ServerApplication> apps = modules
				.stream()
				.map(module -> createServer(module)).collect(Collectors.toList());

		ServerRunner runner;
		try {

			runner = new ServerRunner(
					springContext.getBean(ApplicationRegister.class), apps, end);
		} catch (BeansException e) {
			runner = new ServerRunner(apps, end);
		}

		return runner.run();
	}

	private ServerApplication createServer(Module module) {
		ServerApplication app = new GrizzlyApplicationFactory(springContext,
				module).createApp();
		
		if(Config.instance().getSslProperties()!=null)
			return app.withSSLProperties(Config.instance().getSslProperties());
		else
			return app;
	}

	private void join(Thread thread) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			softener.throwSoftenedException(e);
		}
	}

}