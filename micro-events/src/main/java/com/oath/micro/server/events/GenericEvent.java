package com.oath.micro.server.events;

import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericEvent<T> {

    private GenericEventData<T> data;

    public static <T> GenericEvent<T> trigger(String name, EventBus bus) {
        return trigger(name, bus, null, new String[]{});
    }

    public static <T> GenericEvent<T> trigger(String name, EventBus bus, String[] subTypes) {
        return trigger(name, bus, null, subTypes);
    }

    public static <T> GenericEvent<T> trigger(String name, EventBus bus, T data, String[] subTypes) {
        GenericEvent<T> event = new GenericEvent<>(GenericEventData.<T>builder()
                                                           .name(name)
                                                           .data(data)
                                                           .subTypes(subTypes)
                                                           .build());
        bus.post(event);
        return event;
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class GenericEventData<T> extends BaseEventInfo {
        private final String name;
        private final String[] subTypes;
        private final T data;
    }
}
