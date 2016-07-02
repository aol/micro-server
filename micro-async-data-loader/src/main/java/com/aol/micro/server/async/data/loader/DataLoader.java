package com.aol.micro.server.async.data.loader;

import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;
import com.aol.micro.server.manifest.ManifestComparator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DataLoader implements ScheduledJob{
	private final ManifestComparator<String> comparator;
	@Getter
	private final String cron;
	
	@Override
	public SystemData<String,String> scheduleAndLog() {
		try{
			boolean changed = comparator.isOutOfDate();
			comparator.load();
			return SystemData.<String,String>builder().errors(0).processed(changed?1:0).build();
		}catch(Exception e){
			return SystemData.<String,String>builder().errors(1).processed(0).build();
		}
	}

}
