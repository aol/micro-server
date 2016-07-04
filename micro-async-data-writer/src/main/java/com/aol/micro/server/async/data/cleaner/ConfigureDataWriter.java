package com.aol.micro.server.async.data.cleaner;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.micro.server.async.data.writer.AsyncDataWriter;
import com.aol.micro.server.async.data.writer.MultiDataWriter;
import com.aol.micro.server.manifest.ManifestComparator;

@Configuration
public class ConfigureDataWriter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required=false)
	private List<ManifestComparator> defaultComparators = ListX.empty();
	@Value("${asyc.data.writer.threads:1}")
	private int writerThreads=1;
	@Value("${asyc.data.writer.multi:false}")
	private boolean multiWriterOn=false;
	
	@Bean
	public AsyncDataWriter<?> defaultDataWriter(){
		if(defaultComparators.size()>0){
			System.err.println("Warning :: multiple ManifestComparators configured as Spring bean, using the first configured bean for the Default DataWriter, recommended approach is to configure your own DataWriters as needed.");
			logger.warn("Warning :: multiple ManifestComparators configured as Spring bean, using the first configured bean for the Default DataWriter, recommended approach is to configure your own DataWriters as needed.");
		}
		return new AsyncDataWriter(asyncDataWriterThreadPool(),defaultComparators.get(0));
	}
	@Bean
	public MultiDataWriter<?> defaultMultiDataWriter(){
		if(multiWriterOn)
			return new MultiDataWriter(ListX.fromIterable(defaultComparators).map(mc->new AsyncDataWriter(asyncDataWriterThreadPool(),mc)));
		return new MultiDataWriter(ListX.empty());
	}
	
	@Bean
	public Executor asyncDataWriterThreadPool(){
		return Executors.newFixedThreadPool(writerThreads);
	}
	
	
}
