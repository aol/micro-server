package app.multi.events.com.aol.micro.server;

import org.springframework.stereotype.Component;

import com.oath.micro.server.events.ScheduledJob;
import com.oath.micro.server.events.SystemData;

@Component
public class Job  implements ScheduledJob<Job>{

	@Override
	public SystemData<String,String> scheduleAndLog() {
		return SystemData.<String,String>builder().errors(0).processed(2).build();
	}

}
