package com.aol.micro.server.machine.stats.sigar;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Builder;

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
}
