package com.aol.micro.server.events;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;

import com.aol.micro.server.events.RequestTypes.RequestData;
import com.google.common.eventbus.EventBus;

import lombok.Getter;

public class RequestsBeingExecuted<T> {

    private final EventBus bus;
    final ActiveEvents<RequestData<T>> events = new ActiveEvents<>();
    @Getter
    private final String type;

    public RequestsBeingExecuted(@Qualifier("microserverEventBus") EventBus bus, boolean queryCapture) {
        this.bus = bus;
        this.type = "default";
        if (queryCapture)
            bus.register(this);

    }

    public RequestsBeingExecuted(EventBus bus, String type) {
        this.bus = bus;
        this.type = type;
        bus.register(this);

    }

    public int events() {
        return events.events();
    }

    public int size() {
        return events.size();
    }

    @Override
    public String toString() {
        return events.toString();
    }

    Map toMap() {
        return events.toMap();
    }
}
