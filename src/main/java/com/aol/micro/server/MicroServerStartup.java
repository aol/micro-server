package com.aol.micro.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.module.Module;
import com.aol.micro.server.servers.ApplicationRegister;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.ServerRunner;
import com.aol.micro.server.servers.grizzly.GrizzlyApplicationFactory;
import com.aol.micro.server.spring.SpringContextFactory;
import com.google.common.collect.Lists;

public class MicroServerStartup {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<Module> modules;
	private final CompletableFuture end = new CompletableFuture();

	@Getter
	private final AnnotationConfigWebApplicationContext springContext;

	public MicroServerStartup(Class c, Module... modules) {
		
		this.modules = Lists.newArrayList(modules);
		springContext = new SpringContextFactory(c).createSpringContext();

	}

	public MicroServerStartup(List<Class> additionalClasses, Module... modules) {
		this.modules = Lists.newArrayList(modules);
		
		springContext = new SpringContextFactory(additionalClasses).createSpringContext();
	}

	public void stop(){
		end.complete(true);
	}
	public void run() {
		start().forEach(thread -> join(thread));
	}


	public List<Thread> start() {

		List<ServerApplication> apps = modules.stream().map(module -> 
						new GrizzlyApplicationFactory(springContext,module).createApp()).collect(Collectors.toList());

		ServerRunner runner;
		try{
			
			runner = new ServerRunner(springContext.getBean(ApplicationRegister.class), apps,end);
		}catch(BeansException e){
			runner = new ServerRunner(apps,end);
		}
		
		return runner.run();
	}
	private void join(Thread thread) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
	



	
}