package com.aol.micro.server.events;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.makeAccessible;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


public class JobsBeingExecutedTest {

	JobsBeingExecuted jobs;
	
	ProceedingJoinPoint pjp;
	EventBus bus;
	SystemData data;
	SystemData incoming;
	
	
	@Before
	public void setUp() throws Exception {
		data = SystemData.builder().dataMap(Maps.newHashMap()).errors(1).processed(100).build();
		bus = new EventBus();
		bus.register(this);
		jobs = new JobsBeingExecuted(bus,10);
		pjp = Mockito.mock(ProceedingJoinPoint.class);
	}
	
	@Subscribe public void event(SystemData info){
		this.incoming = info;
	}
	

	@Test
	public void testExecute() throws Throwable {
		Signature sig = Mockito.mock( Signature.class);
		Mockito.when(pjp.getSignature()).thenReturn(sig);
		when(pjp.getTarget()).thenReturn(this);
		Mockito.when(sig.getDeclaringType()).thenReturn(String.class);
		jobs.aroundScheduledJob(pjp);
		verify(pjp,times(1)).proceed();
	}
	
	@Test
	public void testExecuteWithEvent() throws Throwable {
		Signature sig = Mockito.mock( Signature.class);
		when(pjp.getSignature()).thenReturn(sig);
		when(sig.getDeclaringType()).thenReturn(String.class);
		when(pjp.proceed()).thenReturn(data);
		when(pjp.getTarget()).thenReturn(this);
		jobs.aroundScheduledJob(pjp);
		verify(pjp,times(1)).proceed();
		assertThat( incoming,is(notNullValue()));
		
		
	}
	@Test
	public void testExecuteId() throws Throwable {
		Signature sig = Mockito.mock( Signature.class);
		when(pjp.getSignature()).thenReturn(sig);
		when(sig.getDeclaringType()).thenReturn(String.class);
		when(pjp.proceed()).thenReturn(data);
		when(pjp.getTarget()).thenReturn(this);
		jobs.aroundScheduledJob(pjp);
		verify(pjp,times(1)).proceed();

		
		assertThat(incoming.getCorrelationId(),containsString("id_java.lang.String"));
	}
	@Test
	public void testOverflow() throws Throwable {
		when(pjp.getTarget()).thenReturn(this);
		Field field = findField(ConcurrentHashMultiset.class,"countMap");
		makeAccessible(field);
		ConcurrentMap map = (ConcurrentMap) getField(field, jobs.getStatCounter());
		map.put("java.lang.String",Integer.MAX_VALUE);
		testExecute();
	}

}
