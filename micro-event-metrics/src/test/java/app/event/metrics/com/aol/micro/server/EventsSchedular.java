package app.event.metrics.com.aol.micro.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventsSchedular {

    private final EventsJob job;

    @Autowired
    public EventsSchedular(final EventsJob job) {
        this.job = job;
    }

    @Scheduled(fixedDelay = 1)
    public synchronized void scheduleTask() {
        job.scheduleAndLog();
    }
}
