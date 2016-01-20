package app.boot.events.com.aol.micro.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Schedular {

	private final Job job;
	
	@Autowired
	public Schedular(final Job job){
		this.job=job;
	}
	
	@Scheduled(fixedDelay=1)
	public synchronized void scheduleTask(){
		job.scheduleAndLog();
	}
}
