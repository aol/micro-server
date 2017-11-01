package app1.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oath.micro.server.events.ScheduledJob;
import com.oath.micro.server.events.SystemData;
import com.oath.micro.server.utility.HashMapBuilder;

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
				HashMapBuilder.map("time", time).build() ).build();
	}

}
