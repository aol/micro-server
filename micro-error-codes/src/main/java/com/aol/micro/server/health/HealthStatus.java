package com.aol.micro.server.health;

import java.io.Serializable;
import java.util.Queue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


import cyclops.collections.QueueX;
import lombok.AllArgsConstructor;
import lombok.Getter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "health-status")
@XmlType(name = "")
@AllArgsConstructor
public class HealthStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum State {
        Ok, Untested, Errors, Fatal
    }

    @Getter
    @XmlElement(name = "general-processing")
    private final State generalProcessing;

    @Getter
    @XmlElement(name = "recent-errors")
    private final Queue<ErrorEvent> recentErrors;
    @Getter
    @XmlElement(name = "fatal-errors")
    private final Queue<ErrorEvent> fatalErrors;

    public HealthStatus() {
        generalProcessing = State.Untested;
        recentErrors = QueueX.empty();
        fatalErrors = QueueX.empty();
    }
}
