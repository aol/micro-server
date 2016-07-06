package com.aol.micro.server.health;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.annotations.VisibleForTesting;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "health-status")
@XmlType(name = "")
public class HealthStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum State {
        Ok, Unavailable, Not_Applicable, Untested, Errors, Running
    }

    @XmlElement(name = "general-processing")
    State generalProcessing = State.Untested;

    @XmlElement(name = "recent-errors")
    List<ErrorEvent> recentErrors = null;

    public State getGeneralProcessing() {
        return generalProcessing;
    }

    @VisibleForTesting
    public List<ErrorEvent> getRecentErrors() {
        return recentErrors;
    }

}
