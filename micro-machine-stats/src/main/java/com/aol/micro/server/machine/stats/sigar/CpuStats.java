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
@XmlRootElement(name = "cpu-stats")
@XmlType(name = "")
@Getter
@ToString
public class CpuStats implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "idle-percentage")
	private final Double idlePercentage;

	@XmlElement(name = "total-cores")
	private final Integer totalCores;

	private final String model;

	private final Integer mhz;

	@XmlElement(name = "load-average")
	private Double loadAverage;

	@Builder
	private CpuStats(double idlePercentage, int totalCores, String model, int mhz, double loadAverage) {
		this.idlePercentage = idlePercentage;
		this.totalCores = totalCores;
		this.model = model;
		this.mhz = mhz;
		this.loadAverage = loadAverage;
	}

	public CpuStats() {
		this.idlePercentage = null;
		this.totalCores = null;
		this.model = null;
		this.mhz = null;
		this.loadAverage = null;		
	}

}
