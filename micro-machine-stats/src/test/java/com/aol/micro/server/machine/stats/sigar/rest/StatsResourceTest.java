package com.aol.micro.server.machine.stats.sigar.rest;


import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hyperic.sigar.Sigar;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.machine.stats.sigar.MachineStats;
import com.aol.micro.server.machine.stats.sigar.MachineStatsChecker;

public class StatsResourceTest {
	
	private StatsResource statsResource;
	private MachineStatsChecker machineStatsChecker;
		
	@Before
	public void setUp() {
		
		
		machineStatsChecker = mock(MachineStatsChecker.class);
		statsResource = new StatsResource(machineStatsChecker);			
	}
	
	@Test
	public void testGetMachineStats() {
		
		when(machineStatsChecker.getStats((Sigar)anyObject())).thenReturn(mock(MachineStats.class));		
		statsResource.getMachineStats();		
		verify(machineStatsChecker, times(1)).getStats((Sigar)anyObject());
		
	}

}
