package com.aol.micro.server.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.aol.cyclops.lambda.monads.SequenceM;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Classes;
import com.aol.micro.server.rest.jackson.JacksonFeature;
import com.aol.micro.server.servers.model.ServerData;

public interface Module {
	
	default <T> Consumer<WebServerProvider<T>> getServerConfigManager(){
		return server->{};
	}
	default <T> Consumer<JaxRsProvider<T>> getResourceConfigManager(){
		return rc->{};
	}
	default List<String> getPackages(){
		return Arrays.asList();
	}
	
	default Map<String,String> getPropertyOverrides(){
		return new HashMap<>();
	}
	default Set<Class> getSpringConfigurationClasses(){
		return new HashSet<Class>(Arrays.asList(Classes.CORE_CLASSES.getClasses()));
	}
	default List<Class> getRestResourceClasses() {
		return Arrays.asList(RestResource.class);
	}
	default List<Class> getRestAnnotationClasses() {
		return Arrays.asList(Rest.class);
	}
	
	default List<String> getDefaultJaxRsPackages(){
		List list = new ArrayList<>();
		list.addAll(SequenceM.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
				.filter(module -> module.servletContextListeners()!=null)
				.flatMapCollection(Plugin::jaxRsPackages)
				
				.toList());
		return list;
	}
	
	default List<Class> getDefaultResources(){
		List list = new ArrayList<>();
		list.add(JacksonFeature.class);
		list.addAll(SequenceM.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
				.filter(module -> module.servletContextListeners()!=null)
				.flatMapCollection(Plugin::jaxRsResources)
				.toList());
		return list;
	}
	
	default List<ServletContextListener> getListeners(ServerData data){
		List<ServletContextListener> list= new ArrayList<>();
		if(data.getRootContext() instanceof WebApplicationContext){
			list.add(new ContextLoaderListener((WebApplicationContext)data
					.getRootContext()));
		}
		
		List<Plugin> modules = PluginLoader.INSTANCE.plugins.get();
		
		list.addAll(SequenceM.fromStream(modules.stream())
				.filter(module -> module.servletContextListeners()!=null)
				.flatMapCollection(Plugin::servletContextListeners)
				.map(fn->fn.apply(data))
				.toList());
		
		return Collections.unmodifiableList( new ArrayList<>(list));
	}
	default List<ServletRequestListener> getRequestListeners(ServerData data){
		List<ServletRequestListener>  list = new ArrayList<>();
		list.addAll(SequenceM.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
				.filter(module -> module.servletRequestListeners()!=null)
				.flatMapCollection(Plugin::servletRequestListeners)
				.map(fn->fn.apply(data))
				.toList());
		return list;
		
	}
	default Map<String,Filter> getFilters(ServerData data) {
		Map<String, Filter> map = new HashMap<>();
		
		SequenceM
				.fromStream(
						PluginLoader.INSTANCE.plugins.get()
								.stream())
				.filter(module -> module.filters() != null)
				.map(module -> module.filters().apply(data))
				.forEach(pluginMap -> map.putAll(pluginMap));
		return map;
	}
	default Map<String,Servlet> getServlets(ServerData data) {
		Map<String, Servlet> map = new HashMap<>();
		SequenceM
				.fromStream(
						PluginLoader.INSTANCE.plugins.get()
								.stream())
				.filter(module -> module.servlets() != null)
				.map(module -> module.servlets().apply(data))
				.forEach(pluginMap -> map.putAll(pluginMap));
		return map;
		
	}
	
	default  String getJaxWsRsApplication(){
		List<String> jaxRsApplications = SequenceM.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
		.filter(module -> module.jaxWsRsApplication()!=null)
		.flatMapOptional(Plugin::jaxWsRsApplication)
		.toList();
		if(jaxRsApplications.size()>1) {
			throw new IncorrectJaxRsPluginsException("ERROR!  Multiple jax-rs application plugins found " + jaxRsApplications);
			
			
		}else if(jaxRsApplications.size()==0){
			throw new IncorrectJaxRsPluginsException("ERROR!  No jax-rs application plugins found ");
			
			
		}
		return jaxRsApplications.get(0);
	}
	default String getProviders(){
		String additional = SequenceM
		.fromStream(
				PluginLoader.INSTANCE.plugins.get()
						.stream()).filter(module -> module.providers()!=null)
						.flatMapCollection(Plugin::providers)
						.join(",");
			
		if(StringUtils.isEmpty(additional))
			return "com.aol.micro.server.rest.providers";
		return "com.aol.micro.server.rest.providers,"+additional;
	}
	
	public String getContext();
}
