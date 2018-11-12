package com.oath.micro.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.oath.cyclops.util.ExceptionSoftener;
import cyclops.companion.Streams;
import cyclops.reactive.collections.mutable.ListX;
import cyclops.reactive.ReactiveSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;


import com.oath.micro.server.config.Config;
import com.oath.micro.server.config.MicroserverConfigurer;
import com.oath.micro.server.module.Module;
import com.oath.micro.server.servers.ApplicationRegister;
import com.oath.micro.server.servers.ServerApplication;
import com.oath.micro.server.servers.ServerApplicationFactory;
import com.oath.micro.server.servers.ServerRunner;
import com.oath.micro.server.spring.SpringContextFactory;

import lombok.Getter;

/**
 * 
 * Startup class for Microserver instance
 * 
 * @author johnmcclean
 *
 */
public class MicroserverApp {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ListX<Module> modules;
    private final CompletableFuture end = new CompletableFuture();

    @Getter
    private final ApplicationContext springContext;
    public final Class[] classes;

    /**
     * This will construct a Spring context for this Microserver instance.
     * The calling class will be used to determine the base package to auto-scan from for Spring Beans
     * It will attempt to pick up an @Microservice annotation first, if not present the package of the calling class
     * will be used.
     * 
     * @param modules Multiple Microservice end points that can be deployed within a single Spring context
     */
    public MicroserverApp(Module... modules) {
        this.modules = ListX.of(modules);
        GlobalState.state.setModules(this.modules);
        initSpringProperties(modules[0]);
        Class c = extractClass();
        springContext = new SpringContextFactory(
                                                 new MicroserverConfigurer().buildConfig(c), extractClass(),
                                                 modules[0].getSpringConfigurationClasses()).createSpringContext();
        classes=null;

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

        this.modules = ListX.of(modules);
        GlobalState.state.setModules(this.modules);
        initSpringProperties(modules[0]);
        springContext = new SpringContextFactory(
                                                 new MicroserverConfigurer().buildConfig(c), c,
                                                 modules[0].getSpringConfigurationClasses()).createSpringContext();
        classes=null;

    }
    MicroserverApp(boolean sb,Class c, Module... modules) {

        this.modules = ListX.of(modules);
        GlobalState.state.setModules(this.modules);
        initSpringProperties(modules[0]);
        springContext =  null;
        classes = new SpringContextFactory(
            new MicroserverConfigurer().buildConfig(c), c,
            modules[0].getSpringConfigurationClasses()).classes();

    }

    private void initSpringProperties(Module m) {

        System.setProperty("server.servlet.context-path", "/" + m.getContext());

    }

    private Class extractClass() {
        try {
            return Class.forName(new Exception().getStackTrace()[2].getClassName());
        } catch (ClassNotFoundException e) {
            throw ExceptionSoftener.throwSoftenedException(e);
        }

    }

    public void stop() {

        end.complete(true);
        Config.reset();

    }

    public void run() {
        start().forEach(thread -> join(thread));
    }

    public List<Thread> start() {

        List<ServerApplication> apps = modules.map(module -> createServer(module));

        ServerRunner runner;
        try {

            runner = new ServerRunner(
                                      springContext.getBean(ApplicationRegister.class), apps, end);
        } catch (BeansException e) {
            runner = new ServerRunner(
                                      apps, end);
        }

        return runner.run();
    }

    private ServerApplication createServer(Module module) {

        List<ServerApplicationFactory> applications = ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                                                                          .stream())
                                                                 .filter(m -> m.serverApplicationFactory() != null)
                                                                 .map(Plugin::serverApplicationFactory)
                                                                 .flatMap(Streams::optionalToStream)
                                                                 .toList();
        if (applications.size() > 1) {
            logger.error("ERROR!  Multiple server application factories found : The solution is remove one these plugins from your classpath ",
                         applications);
            System.err.println("ERROR!  Multiple server application factories found : The solution is remove one these plugins from your classpath "
                    + applications);
            throw new IncorrectNumberOfServersConfiguredException(
                                                                  "Multiple server application factories found : The solution is remove one these plugins from your classpath "
                                                                          + applications);
        } else if (applications.size() == 0) {
            logger.error("ERROR!  No server application factories found. If you using micro-spring-boot don't call MicroserverApp.start() method. A possible solution is add one of micro-grizzly or micro-tomcat to the classpath.");
            System.err.println("ERROR!  No server application factories found. If you using micro-spring-boot don't call MicroserverApp.start() method. A possible solution is add one of micro-grizzly or micro-tomcat to the classpath.");
            throw new IncorrectNumberOfServersConfiguredException(
                                                                  "No server application factories found. If you using micro-spring-boot don't call MicroserverApp.start() method. A possible solution is add one of micro-grizzly or micro-tomcat to the classpath. ");

        }

        ServerApplication app = applications.get(0)
                                            .createApp(module, springContext);        
        return app;
    }    

    private void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread()
                  .interrupt();
            ExceptionSoftener.throwSoftenedException(e);
        }
    }

}
