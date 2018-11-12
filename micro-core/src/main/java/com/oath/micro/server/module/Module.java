package com.oath.micro.server.module;

import com.oath.micro.server.Plugin;
import com.oath.micro.server.PluginLoader;
import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.config.Classes;
import com.oath.micro.server.servers.model.ServerData;
import cyclops.reactive.collections.mutable.MapX;
import cyclops.companion.Streams;
import cyclops.reactive.collections.mutable.ListX;
import cyclops.reactive.collections.mutable.SetX;
import cyclops.reactive.ReactiveSeq;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;

public interface Module {

    default Set<Object> getJaxRsResourceObjects() {
        return PluginLoader.INSTANCE.plugins.get()
                                            .concatMap(Plugin::jaxRsResourceObjects)
                                            .to().setX();
    }

    default Map<String, Object> getServerProperties() {
        return new HashMap<>();
    }

    default <T> Consumer<WebServerProvider<T>> getServerConfigManager() {
        return server -> {
        };
    }

    default <T> Consumer<JaxRsProvider<T>> getResourceConfigManager() {
        return rc -> {
        };
    }

    default List<String> getPackages() {
        return new ArrayList<>();
    }

    default Map<String, String> getPropertyOverrides() {
        return new HashMap<>();
    }

    default Set<Class<?>> getSpringConfigurationClasses() {
        return SetX.of(Classes.CORE_CLASSES.getClasses());
    }

    default Set<Class<?>> getRestResourceClasses() {
        return SetX.of(RestResource.class);
    }

    default Set<Class<? extends Annotation>> getRestAnnotationClasses() {
        return SetX.of(Rest.class);
    }

    default List<String> getDefaultJaxRsPackages() {

        return PluginLoader.INSTANCE.plugins.get()
                                            .stream()
                                            .filter(module -> module.servletContextListeners() != null)
                                            .concatMap(Plugin::jaxRsPackages)
                                            .to(ListX::fromIterable);

    }

    default List<Class<?>> getDefaultResources() {
        return PluginLoader.INSTANCE.plugins.get()
                                            .stream()
                                            .concatMap(Plugin::jaxRsResources)
                                            .to(ListX::fromIterable);

    }

    default List<ServletContextListener> getListeners(ServerData data) {
        List<ServletContextListener> list = new ArrayList<>();
        if (data.getRootContext() instanceof WebApplicationContext) {
            list.add(new ContextLoaderListener(
                                               (WebApplicationContext) data.getRootContext()));
        }

        ListX<Plugin> modules = PluginLoader.INSTANCE.plugins.get();

        ListX<ServletContextListener> listeners = modules.stream()
                                                           .filter(module -> module.servletContextListeners() != null)
                                                           .concatMap(Plugin::servletContextListeners)
                                                           .map(fn -> fn.apply(data))
                                                           .to(ListX::fromIterable);

        return listeners.plusAll(list);
    }

    default List<ServletRequestListener> getRequestListeners(ServerData data) {

        return PluginLoader.INSTANCE.plugins.get()
                                            .stream()
                                            .filter(module -> module.servletRequestListeners() != null)
                                            .concatMap(Plugin::servletRequestListeners)
                                            .map(fn -> fn.apply(data))
                                             .to(ListX::fromIterable);

    }

    default Map<String, Filter> getFilters(ServerData data) {
        Map<String, Filter> map = new HashMap<>();

        ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                            .stream())
                   .filter(module -> module.filters() != null)
                   .map(module -> module.filters()
                                        .apply(data))
                   .forEach(pluginMap -> map.putAll(pluginMap));
        return MapX.fromMap(map);
    }

    default Map<String, Servlet> getServlets(ServerData data) {
        Map<String, Servlet> map = new HashMap<>();
        ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                            .stream())
                   .filter(module -> module.servlets() != null)
                   .map(module -> module.servlets()
                                        .apply(data))
                   .forEach(pluginMap -> map.putAll(pluginMap));
        return MapX.fromMap(map);

    }

    default String getJaxWsRsApplication() {
        List<String> jaxRsApplications = ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                                                             .stream())
                                                    .filter(module -> module.jaxWsRsApplication() != null)
                                                    .map(Plugin::jaxWsRsApplication)
                                                    .flatMap(Streams::optionalToStream)
                                                    .toList();
        if (jaxRsApplications.size() > 1) {
            throw new IncorrectJaxRsPluginsException(
                                                     "ERROR!  Multiple jax-rs application plugins found,  a possible solution is to remove micro-jackson or other jax-rs application provider from your classpath. "
                                                             + jaxRsApplications);

        } else if (jaxRsApplications.size() == 0) {
            throw new IncorrectJaxRsPluginsException(
                                                     "ERROR!  No jax-rs application plugins found, a possible solution is to add micro-jackson to your classpath. ");

        }
        return jaxRsApplications.get(0);
    }

    default String getProviders() {
        String additional = ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                                                .stream())
                                       .peek(System.out::println)
                                       .filter(module -> module.providers() != null)
                                       .concatMap(Plugin::providers)
                                       .join(",");

        if (StringUtils.isEmpty(additional))
            return "com.oath.micro.server.rest.providers";
        return "com.oath.micro.server.rest.providers," + additional;
    }

    public String getContext();
}
