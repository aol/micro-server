package com.oath.micro.server.machine.stats.sigar;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


import cyclops.reactive.companion.MapXs;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

@SuppressWarnings("PMD.UnusedPrivateField")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "machine-stats")
@XmlType(name = "")
@Getter
@ToString
public class MachineStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "cpu-stats")
    private final CpuStats cpuStats;

    @XmlElement(name = "memory-stats")
    private final MemoryStats memoryStats;

    @XmlElement(name = "swap-stats")
    private final SwapStats swapStats;

    @Builder
    private MachineStats(CpuStats cpuStats, MemoryStats memoryStats, SwapStats swapStats) {
        this.cpuStats = cpuStats;
        this.memoryStats = memoryStats;
        this.swapStats = swapStats;
    }

    public MachineStats() {
        this.cpuStats = null;
        this.memoryStats = null;
        this.swapStats = null;
    }

    public Map<String, Map<String, String>> toMap() {
        return MapXs.of("cpu-stats", cpuStats.toMap(), "memory-stats", memoryStats.toMap(), "swap-stats",
                        swapStats.toMap());
    }
}
