package app1.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;
import com.aol.micro.server.utility.HashMapBuilder;

@Component
public class Job implements ScheduledJob<Job>{

	private final DataService service;
	
	@Autowired
	public Job(DataService service){
		this.service = service;
	}
	
	@Override
	public SystemData scheduleAndLog() {
		Long time =System.currentTimeMillis();
		service.createEntity("time", ""+time);
		return SystemData.<String,Long>builder().errors(0).processed(1).dataMap(
				HashMapBuilder.of("time", time).build() ).build();
	}

}
