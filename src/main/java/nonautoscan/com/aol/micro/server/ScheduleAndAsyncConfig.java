package nonautoscan.com.aol.micro.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.aol.micro.server.SchedulingConfiguration;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.MoreExecutors;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleAndAsyncConfig implements SchedulingConfiguration {

	private int executorThreadPoolSize;
	private int schedulerThreadPoolSize;

	@Autowired @Getter
	private EventBus eventBus;
	
	@Value("${default.task.executor.size:3}")
	public void setExecutorThreadPoolSize(int executorThreadPoolSize) {
		this.executorThreadPoolSize = executorThreadPoolSize;
	}

	@Value("${default.scheduler.executor.size:3}")
	public void setSchedulerThreadPoolSize(int schedulerThreadPoolSize) {
		this.schedulerThreadPoolSize = schedulerThreadPoolSize;
	}

	@Override
	public Executor getAsyncExecutor() {
		return taskExecutor();
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskScheduler() {
		return Executors.newScheduledThreadPool(schedulerThreadPoolSize);
	}

	@Bean
	public Executor taskExecutor() {
		return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(executorThreadPoolSize));
	
	}
}
