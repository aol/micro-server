package app1.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedular {

	private final Job job;
	
	@Autowired
	public Schedular(Job job) {
		this.job = job;
	}

	@Scheduled(fixedDelay=1000)
	public void schedule(){
		job.scheduleAndLog();
	}
}
