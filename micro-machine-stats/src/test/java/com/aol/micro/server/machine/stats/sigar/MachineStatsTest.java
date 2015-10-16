package com.aol.micro.server.machine.stats.sigar;

import org.junit.Test;

public class MachineStatsTest {

	@Test
	public void testEverything() {
		MemoryStats memoryStats = new MemoryStats();
		MemoryStats memoryStats1 = MemoryStats.builder().total(100l).actualFree(30l).freePercent(0.3).actualUsed(70l).usedPercent(0.7).build();

		SwapStats swapStats = new SwapStats();
		SwapStats swapStats1 = SwapStats.builder().pageIn(100l).pageOut(100l).free(100l).used(100l).total(100l).build();

		CpuStats cpuStats = new CpuStats();
		CpuStats cpuStats1 = CpuStats.builder().idlePercentage(0.5).totalCores(36).model("model").mhz(100).loadAverage(0.7).build();
	}

}
