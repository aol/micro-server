package app.events.com.aol.micro.server;

import org.springframework.stereotype.Component;

import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;

@Component
public class Job  implements ScheduledJob<Job>{

	@Override
	public SystemData scheduleAndLog() {
		return SystemData.builder().errors(0).processed(2).build();
	}

}
