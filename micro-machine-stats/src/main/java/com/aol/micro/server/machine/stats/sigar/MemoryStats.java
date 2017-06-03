package com.aol.micro.server.machine.stats.sigar;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


import cyclops.companion.MapXs;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

@SuppressWarnings("PMD.UnusedPrivateField")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "memory-stats")
@XmlType(name = "")
@Getter
@ToString
public class MemoryStats implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long total;

    @XmlElement(name = "actual-free")
    private final Long actualFree;

    @XmlElement(name = "free-percent")
    private final Double freePercent;

    @XmlElement(name = "actual-used")
    private final Long actualUsed;

    @XmlElement(name = "used-percent")
    private final Double usedPercent;

    @Builder
    private MemoryStats(long total, long actualFree, double freePercent, long actualUsed, double usedPercent) {
        this.total = total;
        this.actualFree = actualFree;
        this.freePercent = freePercent;
        this.actualUsed = actualUsed;
        this.usedPercent = usedPercent;
    }

    public MemoryStats() {
        this.total = null;
        this.actualFree = null;
        this.freePercent = null;
        this.actualUsed = null;
        this.usedPercent = null;
    }

    public Map<String, String> toMap() {
        return MapXs.map("total", "" + total)
                    .put("actual-free", "" + actualFree)
                    .put("free-percent", "" + freePercent)
                    .put("actual-used", "" + actualUsed)
                    .put("used-percent", "" + usedPercent)
                    .build();
    }
}
