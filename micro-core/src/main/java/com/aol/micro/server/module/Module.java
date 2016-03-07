package com.aol.micro.server.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.collections.extensions.persistent.PMapX;
import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.cyclops.util.stream.StreamUtils;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Classes;
import com.aol.micro.server.servers.model.ServerData;

public interface Module {
	
	default PMapX<String, Object> getServerProperties() {		
		return PMapX.empty();	
	}
	
	default <T> Consumer<WebServerProvider<T>> getServerConfigManager(){
		return server->{};
	}
	default <T> Consumer<JaxRsProvider<T>> getResourceConfigManager(){
		return rc->{};
	}
	default PStackX<String> getPackages(){
		return PStackX.empty();
	}
	
	default PMapX<String,String> getPropertyOverrides(){
		return PMapX.empty();
	}
	default PSetX<Class> getSpringConfigurationClasses(){
		return PSetX.of(Classes.CORE_CLASSES.getClasses());
	}
	default PStackX<Class> getRestResourceClasses() {
		return PStackX.of(RestResource.class);
	}
	default PStackX<Class> getRestAnnotationClasses() {
		return PStackX.of(Rest.class);
	}
	
	
	default PStackX<String> getDefaultJaxRsPackages(){
		
		return PluginLoader.INSTANCE.plugins.get().stream()
				.filter(module -> module.servletContextListeners()!=null)
				.flatMapIterable(Plugin::jaxRsPackages)
				
				.toPStackX();
		
	}
	
	default PStackX<Class> getDefaultResources(){
		return PluginLoader.INSTANCE.plugins.get().stream()
				.filter(module -> module.servletContextListeners()!=null)
				.flatMapIterable(Plugin::jaxRsResources)
				.toPStackX();
		
	}
	
	default PStackX<ServletContextListener> getListeners(ServerData data){
		List<ServletContextListener> list= new ArrayList<>();
		if(data.getRootContext() instanceof WebApplicationContext){
			list.add(new ContextLoaderListener((WebApplicationContext)data
					.getRootContext()));
		}
		
		ListX<Plugin> modules = PluginLoader.INSTANCE.plugins.get();
		
		PStackX<ServletContextListener> listeners = modules.stream()
														   .filter(module -> module.servletContextListeners()!=null)
													       .flatMapIterable(Plugin::servletContextListeners)
													       .map(fn->fn.apply(data))
													       .toPStackX();
		
		return listeners.plusAll(list);
	}
	default PStackX<ServletRequestListener> getRequestListeners(ServerData data){
		
		return PluginLoader.INSTANCE.plugins.get().stream()
						   .filter(module -> module.servletRequestListeners()!=null)
						   .flatMapIterable(Plugin::servletRequestListeners)
						   .map(fn->fn.apply(data))
						   .toPStackX();
		
		
	}
	default PMapX<String,Filter> getFilters(ServerData data) {
		Map<String, Filter> map = new HashMap<>();
		
		ReactiveSeq
				.fromStream(
						PluginLoader.INSTANCE.plugins.get()
								.stream())
				.filter(module -> module.filters() != null)
				.map(module -> module.filters().apply(data))
				.forEach(pluginMap -> map.putAll(pluginMap));
		return PMapX.fromMap(map);
	}
	default PMapX<String,Servlet> getServlets(ServerData data) {
		Map<String, Servlet> map = new HashMap<>();
		ReactiveSeq
				.fromStream(
						PluginLoader.INSTANCE.plugins.get()
								.stream())
				.filter(module -> module.servlets() != null)
				.map(module -> module.servlets().apply(data))
				.forEach(pluginMap -> map.putAll(pluginMap));
		return PMapX.fromMap(map);
		
	}
	
	default  String getJaxWsRsApplication(){
		List<String> jaxRsApplications = ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
		.filter(module -> module.jaxWsRsApplication()!=null)
		.map(Plugin::jaxWsRsApplication)
		.flatMap(StreamUtils::optionalToStream)
		.toList();
		if(jaxRsApplications.size()>1) {
			throw new IncorrectJaxRsPluginsException("ERROR!  Multiple jax-rs application plugins found " + jaxRsApplications);
			
			
		}else if(jaxRsApplications.size()==0){
			throw new IncorrectJaxRsPluginsException("ERROR!  No jax-rs application plugins found ");
			
			
		}
		return jaxRsApplications.get(0);
	}
	default String getProviders(){
		String additional = ReactiveSeq
		.fromStream(
				PluginLoader.INSTANCE.plugins.get()
						.stream())
						.peek(System.out::println)
						.filter(module -> module.providers()!=null)
						.flatMapIterable(Plugin::providers)
						.join(",");
			
		if(StringUtils.isEmpty(additional))
			return "com.aol.micro.server.rest.providers";
		return "com.aol.micro.server.rest.providers,"+additional;
	}
	
	public String getContext();
}
