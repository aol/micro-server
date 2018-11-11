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
@XmlRootElement(name = "swap-stats")
@XmlType(name = "")
@Getter
@ToString
public class SwapStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "page-in")
    private final Long pageIn;

    @XmlElement(name = "page-out")
    private final Long pageOut;

    private final Long free;

    private final Long used;

    private final Long total;

    @Builder
    private SwapStats(long pageIn, long pageOut, long free, long used, long total) {
        this.pageIn = pageIn;
        this.pageOut = pageOut;
        this.free = free;
        this.used = used;
        this.total = total;
    }

    public SwapStats() {
        this.pageIn = null;
        this.pageOut = null;
        this.free = null;
        this.used = null;
        this.total = null;
    }

    public Map<String, String> toMap() {
        return MapXs.map("page-in", "" + pageIn)
                    .put("page-out", "" + pageOut)
                    .put("free", "" + used)
                    .put("total", "" + total)
                    .build();
    }
}
