package nonautoscan.com.aol.micro.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.aol.micro.server.SchedulingConfiguration;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleAndAsyncConfig implements SchedulingConfiguration {
	
	private int executorThreadPoolSize;
	private int schedulerThreadPoolSize;

	
	
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
		return Executors.newFixedThreadPool(executorThreadPoolSize);
	
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

	
}
