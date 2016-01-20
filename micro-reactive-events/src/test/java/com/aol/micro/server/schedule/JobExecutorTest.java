package com.aol.micro.server.schedule;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;

public class JobExecutorTest {

	ScheduledExecutorService ex =Executors.newScheduledThreadPool(1);
	JobExecutor executor = new JobExecutor(ex);
	AtomicInteger count = new AtomicInteger(0);
	
	ScheduledJob<JobExecutorTest> myJob = new ScheduledJob<JobExecutorTest>(){

		@Override
		public SystemData<String,String> scheduleAndLog() {
			count.incrementAndGet();
			return SystemData.<String,String>builder().build();
		}
		
	};
	@Test
	public void cronDebounceTest() throws InterruptedException{
		
		assertThat(executor.schedule("* * * * * ?", myJob)
				.connect()
				.limit(4)
				.debounce(1,TimeUnit.DAYS)
				.peek(System.out::println)
				.toList().size(),equalTo(1));
		
		
	}
	
	@Test
	public void fixedRateTest() throws InterruptedException{
		assertThat(executor.scheduleFixedRate(100, myJob)
				.connect()
				.limit(4)
				.debounce(1,TimeUnit.DAYS)
				.peek(System.out::println)
				.toList().size(),equalTo(1));
		
		
	}
	
	@Test
	public void fixedRateDelay() throws InterruptedException{
		assertThat(executor.scheduleFixedDelay(1000, myJob)
				.connect()
				.limit(4)
				.debounce(1,TimeUnit.DAYS)
				.peek(System.out::println)
				.toList().size(),equalTo(1));
		
		
	}
}
