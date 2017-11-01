package com.oath.micro.server.machine.stats.sigar;

import java.util.Map;

import cyclops.companion.MapXs;
import org.hyperic.sigar.Sigar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oath.micro.server.StatsSupplier;

@Component
public class SigarStats implements StatsSupplier {
    private final MachineStatsChecker checker;
    private final long statsDelay;
    private final boolean cpuOnly;
    private volatile long lastRan = 0;
    private volatile boolean lastRanValue = true;

    public SigarStats(MachineStatsChecker checker, @Value("${stats.checker.delay:1000}") long statsDelay,
            @Value("${stats.checker.cpu.only:true}") boolean cpuOnly) {
        this.statsDelay = statsDelay;
        this.checker = checker;
        this.cpuOnly = cpuOnly;
    }

    @Override
    public Map<String, Map<String, String>> get() {
        if (cpuOnly)
            return MapXs.of("cpu-stats", checker.cpuStats(new Sigar())
                                                .toMap());
        MachineStats stats = checker.getStats(new Sigar());
        return stats.toMap();
    }

}
