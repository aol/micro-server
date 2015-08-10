package com.aol.micro.server.machine.stats.sigar;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Component;

import com.aol.advertising.lana.common.loggers.FunctionalAreaPrefix;
import com.aol.advertising.lana.common.loggers.LanaLogger;

@Component
public class MachineStatsChecker {

	private LanaLogger logger = LanaLogger.getLogger(FunctionalAreaPrefix.COMMON_STATS, MachineStatsChecker.class);

	public MachineStats getStats(Sigar sigar) {

		SwapStats.SwapStatsBuilder swapStats = SwapStats.builder();
		CpuStats.CpuStatsBuilder cpuStats = CpuStats.builder();
		MemoryStats.MemoryStatsBuilder memoryStats = MemoryStats.builder();

		try {
			getSwapStats(swapStats, sigar);
			getCpuStats(cpuStats, sigar);
			getMemoryStats(memoryStats, sigar);

		} catch (SigarException e) {
			logger.error("Error during sigar stats operation", e);
		} finally {
			sigar.close();
		}

		return getMachineStats(swapStats, cpuStats, memoryStats);

	}

	private void getSwapStats(SwapStats.SwapStatsBuilder swapStats, Sigar sigar) throws SigarException {
		swapStats.pageIn(sigar.getSwap().getPageIn());
		swapStats.pageOut(sigar.getSwap().getPageOut());
		swapStats.free(sigar.getSwap().getFree());
		swapStats.used(sigar.getSwap().getUsed());
		swapStats.total(sigar.getSwap().getTotal());
	}

	private void getCpuStats(CpuStats.CpuStatsBuilder cpuStats, Sigar sigar) throws SigarException {
		cpuStats.idlePercentage(sigar.getCpuPerc().getIdle());
		cpuStats.totalCores(sigar.getCpuInfoList()[0].getTotalCores());
		cpuStats.model(sigar.getCpuInfoList()[0].getModel());
		cpuStats.mhz(sigar.getCpuInfoList()[0].getMhz());
		cpuStats.loadAverage(sigar.getLoadAverage()[0]);
	}

	private void getMemoryStats(MemoryStats.MemoryStatsBuilder memoryStats, Sigar sigar) throws SigarException {
		memoryStats.total(sigar.getMem().getTotal());
		memoryStats.actualFree(sigar.getMem().getActualFree());
		memoryStats.freePercent(sigar.getMem().getFreePercent());
		memoryStats.actualUsed(sigar.getMem().getActualUsed());
		memoryStats.usedPercent(sigar.getMem().getUsedPercent());
	}

	private MachineStats getMachineStats(SwapStats.SwapStatsBuilder swapStats, CpuStats.CpuStatsBuilder cpuStats,
			MemoryStats.MemoryStatsBuilder memoryStats) {
		MachineStats.MachineStatsBuilder machineStats = MachineStats.builder();
		machineStats.swapStats(swapStats.build());
		machineStats.cpuStats(cpuStats.build());
		machineStats.memoryStats(memoryStats.build());
		return machineStats.build();
	}
}
