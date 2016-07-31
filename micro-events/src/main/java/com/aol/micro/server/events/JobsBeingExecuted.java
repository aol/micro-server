package com.aol.micro.server.events;

import java.util.Optional;
import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.eventbus.EventBus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Aspect
public class JobsBeingExecuted {

    private static final Random r = new Random();
    private final ActiveEvents<JobExecutingData> events = new ActiveEvents<JobExecutingData>();
    @Getter(AccessLevel.PACKAGE)
    private final ConcurrentHashMultiset<String> statCounter = ConcurrentHashMultiset.create();
    private final EventBus eventBus;

    private final LoggingRateLimiter<Class> loggingRateLimiter;

    private final int maxLoggingCapacity;

    public JobsBeingExecuted(@Qualifier("microserverEventBus") EventBus bus,
            @Value("${system.logging.max.per.hour:10}") int maxLoggingCapacity) {
        this.eventBus = bus;
        this.loggingRateLimiter = new LoggingRateLimiter<>();
        this.maxLoggingCapacity = maxLoggingCapacity;
    }

    public JobsBeingExecuted(EventBus bus) {
        this(
             bus, 10);
    }

    @Around("execution(* com.aol.micro.server.events.ScheduledJob.scheduleAndLog(..))")
    public Object aroundScheduledJob(ProceedingJoinPoint pjp) throws Throwable {
        String type = pjp.getSignature()
                         .getDeclaringType()
                         .getName();

        return executeScheduledJob(pjp, type);

    }

    public int size() {
        return events.size();
    }

    public int events() {
        return events.events();
    }

    @Override
    public String toString() {
        return events.toString();
    }

    private Object executeScheduledJob(final ProceedingJoinPoint pjp, final String type) throws Throwable {
        addTypeToStatCounter(type);
        JobExecutingData data = new JobExecutingData(
                                                     type, statCounter.count(type));
        String id = buildId(type, data.getProcessingThread());
        events.active(id, data);

        SystemData retVal = null;
        long correlationId = r.nextLong();
        eventBus.post(new JobStartEvent(
                                        correlationId, type));
        try {

            retVal = Optional.ofNullable(((SystemData) pjp.proceed()))
                             .map(sd -> sd.withCorrelationId(id))
                             .orElse(null);
            return retVal;
        } finally {
            logSystemEvent(pjp, type, data, retVal);
            eventBus.post(new JobCompleteEvent(
                                               correlationId, type));
        }
    }

    private void logSystemEvent(final ProceedingJoinPoint pjp, final String type, JobExecutingData data,
            SystemData retVal) {
        final SystemData active = retVal;
        loggingRateLimiter.addAndEnsureFrequency(pjp.getTarget()
                                                    .getClass());
        loggingRateLimiter.capacityAvailable(pjp.getTarget()
                                                .getClass(),
                                             10, new Runnable() {
                                                 @Override
                                                 public void run() {
                                                     postEvent(pjp, type, data, active);
                                                 }
                                             });
    }

    private void postEvent(ProceedingJoinPoint pjp, String type, JobExecutingData data, SystemData retVal) {
        if (retVal != null) {

            eventBus.post(retVal);
        }
        events.finished(buildId(type, data.getProcessingThread()));
    }

    private void addTypeToStatCounter(String type) {
        try {
            statCounter.add(type);
        } catch (Exception e) {
            statCounter.clear();
            statCounter.add(type);
        }
    }

    private String buildId(String type, long threadId) {
        return "id_" + type + "-" + threadId;
    }

    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    static class JobExecutingData extends BaseEventInfo {
        private final String type;
        private final int timesExecuted;
    }
}
