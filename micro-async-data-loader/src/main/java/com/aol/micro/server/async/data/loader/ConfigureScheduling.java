package com.aol.micro.server.async.data.loader;

import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.control.Maybe;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.micro.server.manifest.ManifestComparator;
import com.google.common.eventbus.EventBus;

@Configuration
public class ConfigureScheduling {

	@Value("${asyc.data.schedular.cron.loader:0 0 * * * *}")
	private String defaultCron;
	
	@Value("${asyc.data.schedular.threads:5}")
	private int schedularThreads;
	
	@Autowired(required=false)
	private List<DataLoader> dataLoaders= ListX.empty();;
	
	
	
	@Autowired
	private EventBus bus;
	
	@Autowired(required=false)
	private List<ManifestComparator> defaultComparators;
	
	
	private ListX<DataLoader> dataLoaders(){
		Maybe<DataLoader> defaultDataLoader = defaultComparators.size()==1  ?
							Maybe.just(new DataLoader(defaultComparators.get(0),defaultCron))
							: Maybe.none();
		return ListX.fromIterable(defaultDataLoader)
			.plusAll(dataLoaders);
		
		     
	}
	
	@Bean
	public LoaderSchedular asyncDataLoader(){
		LoaderSchedular schedular =  new LoaderSchedular(dataLoaders(),Executors.newScheduledThreadPool(schedularThreads) , bus);
		schedular.schedule();
		return schedular;
	}
	
}