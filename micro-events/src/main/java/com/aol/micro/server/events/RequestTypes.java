package com.aol.micro.server.events;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.aol.cyclops.data.collections.extensions.standard.MapX;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

public class RequestTypes<T> {
    @Getter
    private final Map<String, RequestsBeingExecuted> map = new ConcurrentHashMap<>();;

    private final EventBus bus;

    public RequestTypes(EventBus bus, boolean queryCapture) {
        this.bus = bus;
        if (queryCapture)
            bus.register(this);

    }

    @Override
    public String toString() {
        Map result = toMap();
        return JacksonUtil.serializeToJson(result);
    }

    public Map toMap() {
        return MapX.fromMap(map)
                   .bimap(k -> k, v -> v.toMap());
    }

    @Subscribe
    public void finished(RemoveQuery<T> data) {
        String key = data.getData().type;
        map.computeIfAbsent(key, k -> new RequestsBeingExecuted<T>(
                                                                   bus, k)).events.finished(buildId(data.getData()));

    }

    @Subscribe
    public void processing(AddQuery<T> data) {
        String id = buildId(data.getData());
        String key = data.getData().type;
        map.computeIfAbsent(key, k -> new RequestsBeingExecuted<T>(
                                                                   bus, k)).events.active(id, data.getData());

    }

    private String buildId(RequestData data) {
        String id = "" + data.correlationId;
        return id;
    }

    public static class AddQuery<T> extends AddEvent<RequestData<T>> {

        public AddQuery(RequestData<T> data) {
            super(
                  data);
        }

    }

    public static class RemoveQuery<T> extends RemoveEvent<RequestData<T>> {

        public RemoveQuery(RequestData data) {
            super(
                  data);
        }

    }

    @AllArgsConstructor
    @Builder
    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    public static class RequestData<T> extends BaseEventInfo {

        private final long correlationId;

        private final T query;

        private final String type;
        private final Object additionalData;
    }
}
