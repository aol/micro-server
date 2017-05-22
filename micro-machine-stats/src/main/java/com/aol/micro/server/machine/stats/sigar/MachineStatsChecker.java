package com.aol.micro.server.machine.stats.sigar;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MachineStatsChecker {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MachineStats getStats(Sigar sigar) {
        CpuStats cpuStats = CpuStats.builder()
                                    .build();
        SwapStats swapStats = SwapStats.builder()
                                       .build();
        MemoryStats memoryStats = MemoryStats.builder()
                                             .build();
        try {
            swapStats = getSwapStats(sigar);
            cpuStats = getCpuStats(sigar);
            getMemoryStats(sigar);

        } catch (SigarException | UnsatisfiedLinkError e) {
            logger.error("Error during sigar stats operation", e);
        } finally {
            sigar.close();
        }

        return getMachineStats(swapStats, cpuStats, memoryStats);

    }

    CpuStats cpuStats(Sigar sigar) {
        try {
            return getCpuStats(sigar);

        } catch (SigarException | UnsatisfiedLinkError e) {
            logger.error("Error during sigar stats operation", e);
        } finally {
            sigar.close();
        }
        return CpuStats.builder()
                       .build();
    }

    private SwapStats getSwapStats(Sigar sigar) throws SigarException {
        SwapStats.SwapStatsBuilder swapStats = SwapStats.builder();
        swapStats.pageIn(sigar.getSwap()
                              .getPageIn());
        swapStats.pageOut(sigar.getSwap()
                               .getPageOut());
        swapStats.free(sigar.getSwap()
                            .getFree());
        swapStats.used(sigar.getSwap()
                            .getUsed());
        swapStats.total(sigar.getSwap()
                             .getTotal());
        return swapStats.build();
    }

    private CpuStats getCpuStats(Sigar sigar) throws SigarException {
        CpuStats.CpuStatsBuilder cpuStats = CpuStats.builder();
        cpuStats.idlePercentage(sigar.getCpuPerc()
                                     .getIdle());
        cpuStats.totalCores(sigar.getCpuInfoList()[0].getTotalCores());
        cpuStats.model(sigar.getCpuInfoList()[0].getModel());
        cpuStats.mhz(sigar.getCpuInfoList()[0].getMhz());
        cpuStats.loadAverage(sigar.getLoadAverage()[0]);
        return cpuStats.build();
    }

    private MemoryStats getMemoryStats(Sigar sigar) throws SigarException {
        MemoryStats.MemoryStatsBuilder memoryStats = MemoryStats.builder();
        memoryStats.total(sigar.getMem()
                               .getTotal());
        memoryStats.actualFree(sigar.getMem()
                                    .getActualFree());
        memoryStats.freePercent(sigar.getMem()
                                     .getFreePercent());
        memoryStats.actualUsed(sigar.getMem()
                                    .getActualUsed());
        memoryStats.usedPercent(sigar.getMem()
                                     .getUsedPercent());
        return memoryStats.build();
    }

    private MachineStats getMachineStats(SwapStats swapStats, CpuStats cpuStats, MemoryStats memoryStats) {
        MachineStats.MachineStatsBuilder machineStats = MachineStats.builder();
        machineStats.swapStats(swapStats);
        machineStats.cpuStats(cpuStats);
        machineStats.memoryStats(memoryStats);
        return machineStats.build();
    }
}
