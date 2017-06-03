package com.aol.micro.server.module;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Classes;
import com.aol.micro.server.servers.model.ServerData;
import cyclops.companion.Streams;
import cyclops.collections.mutable.ListX;
import cyclops.collections.mutable.SetX;
import cyclops.collections.immutable.PersistentMapX;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.immutable.LinkedListX;
import cyclops.stream.ReactiveSeq;
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
                                            .flatMap(Plugin::jaxRsResourceObjects)
                                            .to().persistentSetX();
    }

    default Map<String, Object> getServerProperties() {
        return PersistentMapX.empty();
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
        return LinkedListX.empty();
    }

    default Map<String, String> getPropertyOverrides() {
        return PersistentMapX.empty();
    }

    default Set<Class<?>> getSpringConfigurationClasses() {
        return PersistentSetX.of(Classes.CORE_CLASSES.getClasses());
    }

    default Set<Class<?>> getRestResourceClasses() {
        return PersistentSetX.of(RestResource.class);
    }

    default Set<Class<? extends Annotation>> getRestAnnotationClasses() {
        return SetX.of(Rest.class);
    }

    default List<String> getDefaultJaxRsPackages() {

        return PluginLoader.INSTANCE.plugins.get()
                                            .stream()
                                            .filter(module -> module.servletContextListeners() != null)
                                            .flatMapI(Plugin::jaxRsPackages)

                                            .to().linkedListX();

    }

    default List<Class<?>> getDefaultResources() {
        return PluginLoader.INSTANCE.plugins.get()
                                            .stream()
                                            .flatMapI(Plugin::jaxRsResources)
                                            .to().linkedListX();

    }

    default List<ServletContextListener> getListeners(ServerData data) {
        List<ServletContextListener> list = new ArrayList<>();
        if (data.getRootContext() instanceof WebApplicationContext) {
            list.add(new ContextLoaderListener(
                                               (WebApplicationContext) data.getRootContext()));
        }

        ListX<Plugin> modules = PluginLoader.INSTANCE.plugins.get();

        LinkedListX<ServletContextListener> listeners = modules.stream()
                                                           .filter(module -> module.servletContextListeners() != null)
                                                           .flatMapI(Plugin::servletContextListeners)
                                                           .map(fn -> fn.apply(data))
                                                           .to().linkedListX();

        return listeners.plusAll(list);
    }

    default List<ServletRequestListener> getRequestListeners(ServerData data) {

        return PluginLoader.INSTANCE.plugins.get()
                                            .stream()
                                            .filter(module -> module.servletRequestListeners() != null)
                                            .flatMapI(Plugin::servletRequestListeners)
                                            .map(fn -> fn.apply(data))
                                             .to().linkedListX();

    }

    default Map<String, Filter> getFilters(ServerData data) {
        Map<String, Filter> map = new HashMap<>();

        ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                            .stream())
                   .filter(module -> module.filters() != null)
                   .map(module -> module.filters()
                                        .apply(data))
                   .forEach(pluginMap -> map.putAll(pluginMap));
        return PersistentMapX.fromMap(map);
    }

    default Map<String, Servlet> getServlets(ServerData data) {
        Map<String, Servlet> map = new HashMap<>();
        ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                            .stream())
                   .filter(module -> module.servlets() != null)
                   .map(module -> module.servlets()
                                        .apply(data))
                   .forEach(pluginMap -> map.putAll(pluginMap));
        return PersistentMapX.fromMap(map);

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
                                       .flatMapI(Plugin::providers)
                                       .join(",");

        if (StringUtils.isEmpty(additional))
            return "com.aol.micro.server.rest.providers";
        return "com.aol.micro.server.rest.providers," + additional;
    }

    public String getContext();
}
