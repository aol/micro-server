package com.aol.micro.server.async.data.loader;

import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;
import com.aol.micro.server.manifest.ManifestComparator;

import app.events.com.aol.micro.server.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DataCleaner<T>  implements ScheduledJob<Job>{
	
	private final ManifestComparator<T> comparator;
	@Getter
	private final String cron;
	
	
	@Override
	public SystemData<String,String> scheduleAndLog() {
		try{
		        comparator.cleanAll();
			return SystemData.<String,String>builder().errors(0).processed(1).build();
		}catch(Exception e){
			return SystemData.<String,String>builder().errors(1).processed(0).build();
		}
		
	}

}