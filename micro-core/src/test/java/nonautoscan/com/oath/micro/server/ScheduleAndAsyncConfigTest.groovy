package nonautoscan.com.oath.micro.server

import groovy.transform.CompileStatic

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.springframework.scheduling.config.ScheduledTaskRegistrar
@CompileStatic
class ScheduleAndAsyncConfigTest {


	ScheduleAndAsyncConfig config
	@Before
	public void setup(){
		config = new ScheduleAndAsyncConfig()
		config.schedulerThreadPoolSize=3
		config.executorThreadPoolSize=3
	}
	@Test
	public void testSetExecutorThreadPoolSize() {
		assert config.@executorThreadPoolSize==3
	}

	@Test
	public void testSetSchedulerThreadPoolSize() {
		
		assert config.@schedulerThreadPoolSize==3
	}

	@Test
	public void testGetAsyncExecutor() {
		assert config.asyncExecutor !=null
	}

	@Test
	public void testConfigureTasks() {
		ScheduledTaskRegistrar mock = Mockito.mock(ScheduledTaskRegistrar)
		config.configureTasks(mock)
		Mockito.verify(mock).setScheduler(Mockito.anyObject())
	}

	@Test
	public void testTaskScheduler() {
		assert config.taskScheduler()!=null
	}

	@Test
	public void testTaskExecutor() {
		assert config.taskExecutor() != null
	}



}