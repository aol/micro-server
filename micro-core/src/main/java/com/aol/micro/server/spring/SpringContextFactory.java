package com.aol.micro.server.spring;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import org.pcollections.HashTreePSet;
import org.pcollections.PSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.aol.cyclops.invokedynamic.ExceptionSoftener;
import com.aol.cyclops.sequence.SequenceM;
import com.aol.micro.server.ErrorCode;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.config.Config;


@AllArgsConstructor
public class SpringContextFactory {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PSet<Class> classes;
	private final Config config;
	@Wither
	private final SpringBuilder springBuilder;
	
	public SpringContextFactory(Config config, Class c, Set<Class> classes){
		Set s = new HashSet(classes);
		s.add(c);
		s.addAll(config.getClasses());
		this.classes = HashTreePSet.from(s);
		this.config = config;
		
		springBuilder = SequenceM
				.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
				.filter(m -> m.springBuilder() != null)
				.map(Plugin::springBuilder)
				.findFirst()
				.orElse(new SpringApplicationConfigurator());
	}
	
	public SpringContextFactory(Config config, Set<Class> classes) {
		Set s = new HashSet(classes);
		s.addAll(config.getClasses());
		this.classes =  HashTreePSet.from(s);
		this.config=config;
		springBuilder = SequenceM
				.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
				.filter(m -> m.springBuilder() != null)
				.map(Plugin::springBuilder)
				.findFirst()
				.orElse(new SpringApplicationConfigurator());
	}

	public ApplicationContext createSpringContext() {
		try {
			ApplicationContext springContext = springBuilder.createSpringApp(config,classes.toArray(new Class[0]));
			return springContext;
		} catch (Exception e) {
			logger.error( ErrorCode.STARTUP_FAILED_SPRING_INITIALISATION.toString(),e.getMessage());
			ExceptionSoftener.throwSoftenedException(e);
		}
		return null;
	}
	
}
