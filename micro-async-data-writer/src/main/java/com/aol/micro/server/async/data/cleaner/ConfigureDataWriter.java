package com.aol.micro.server.async.data.cleaner;

import java.util.List;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.micro.server.async.data.writer.DataWriter;
import com.aol.micro.server.manifest.ManifestComparator;

@Configuration
public class ConfigureDataWriter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required=false)
	private List<ManifestComparator> defaultComparators = ListX.empty();
	@Value("${asyc.data.writer.threads:1}")
	private int writerThreads;
	@Bean
	public DataWriter<?> defaultDataWriter(){
		if(defaultComparators.size()>0){
			System.err.println("Warning :: multiple ManifestComparators configured as Spring bean, using the first configured bean for the Default DataWriter, recommended approach is to configure your own DataWriters as needed.");
			logger.warn("Warning :: multiple ManifestComparators configured as Spring bean, using the first configured bean for the Default DataWriter, recommended approach is to configure your own DataWriters as needed.");
		}
		return new DataWriter(Executors.newFixedThreadPool(1),defaultComparators.get(0));
	}
	
	
}
