package com.aol.micro.server.async.data.cleaner;

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

	@Value("${asyc.data.schedular.cron.cleaner:0 0 * * * ?}")
	private String defaultCronCleaner;
	@Value("${asyc.data.schedular.threads:1}")
	private int schedularThreads;

	@Autowired(required = false)
	private List<DataCleaner> dataCleaners = ListX.empty();

	@Autowired
	private EventBus bus;

	@Autowired(required = false)
	private List<ManifestComparator> defaultComparators;

	private ListX<DataCleaner> dataCleaners() {
		Maybe<DataCleaner> defaultDataCleaner = defaultComparators.size() == 1
				? Maybe.just(new DataCleaner(	defaultComparators.get(0),
												defaultCronCleaner))
				: Maybe.none();
		return ListX.fromIterable(defaultDataCleaner)
					.plusAll(dataCleaners);

	}

	@Bean
	public CleanerSchedular asyncDataCleaner() {
		CleanerSchedular schedular = new CleanerSchedular(	dataCleaners(),
															Executors.newScheduledThreadPool(schedularThreads),
															bus);
		schedular.schedule();
		return schedular;
	}
}
