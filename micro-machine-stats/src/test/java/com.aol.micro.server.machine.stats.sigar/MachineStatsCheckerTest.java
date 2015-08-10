package com.aol.advertising.lana.sigar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.junit.Before;
import org.junit.Test;

public class MachineStatsCheckerTest {
	
	private Sigar sigar;
	private Swap swap;	
	private CpuPerc cpuPerc;
	private CpuInfo cpuInfo;
	private Mem mem;
	
	private MachineStatsChecker machineStatsChecker;

	@Before
	public void setUp() {
		sigar = mock(Sigar.class);
		swap = mock(Swap.class);
		cpuPerc = mock(CpuPerc.class);
		cpuInfo = mock(CpuInfo.class);
		mem = mock(Mem.class);
		
		machineStatsChecker = new MachineStatsChecker();		
	}
	
	@Test
	public void testGetStats() throws SigarException {
		
		when(sigar.getSwap()).thenReturn(swap)
		                     .thenReturn(swap)
		                     .thenReturn(swap)
		                     .thenReturn(swap)
		                     .thenReturn(swap);
		
		when(sigar.getCpuPerc()).thenReturn(cpuPerc)
								.thenReturn(cpuPerc)
								.thenReturn(cpuPerc);
		
		CpuInfo[] cpuInfos = new CpuInfo[] { cpuInfo };
		
		when(sigar.getCpuInfoList()).thenReturn(cpuInfos)
									.thenReturn(cpuInfos)
									.thenReturn(cpuInfos);
		
		double[] doubleValue = new double[] { 0.0 };
		when(sigar.getLoadAverage()).thenReturn(doubleValue);
		
		when(sigar.getMem()).thenReturn(mem)
						    .thenReturn(mem)
						    .thenReturn(mem)
						    .thenReturn(mem)
						    .thenReturn(mem);
		
		
		machineStatsChecker.getStats(sigar);
		
		verify(sigar, times(5)).getSwap();
		verify(swap, times(1)).getPageIn();
		verify(swap, times(1)).getPageOut();
		verify(swap, times(1)).getFree();
		verify(swap, times(1)).getUsed();
		verify(swap, times(1)).getTotal();
		
		verify(sigar, times(1)).getCpuPerc();
		verify(cpuPerc, times(1)).getIdle();
		
		
		verify(sigar, times(3)).getCpuInfoList();
		verify(cpuInfo, times(1)).getTotalCores();
		verify(cpuInfo, times(1)).getMhz();
		verify(cpuInfo, times(1)).getModel();
		
		verify(sigar, times(1)).getLoadAverage();
		
		verify(sigar, times(5)).getMem();
		verify(mem, times(1)).getActualFree();
		verify(mem, times(1)).getActualUsed();
		verify(mem, times(1)).getTotal();
		verify(mem, times(1)).getUsedPercent();
		verify(mem, times(1)).getFreePercent();
		
		verify(sigar, times(1)).close();
		
	}
	
	@Test
	public void testGetStatsSigarExceptio() throws SigarException {
		when(sigar.getSwap()).thenThrow(new SigarException());		
		machineStatsChecker.getStats(sigar);
		verify(sigar, times(1)).getSwap();
		verify(sigar, times(1)).close();
	}

}
