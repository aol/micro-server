package com.oath.micro.server.events;

import com.oath.micro.server.events.RequestTypes.AddLabelledQuery;
import com.oath.micro.server.events.RequestTypes.RemoveLabelledQuery;
import com.oath.micro.server.events.RequestTypes.RequestData;

import com.google.common.eventbus.EventBus;

/**
 * Factory class for creating Start and End events which are identified by a custom label
 *
 */
public class LabelledEvents {

    /**
     * Publish start events for each of the specified query types
     *
     * <pre>
     * {@code
        LabelledEvents.start("get", 1l, bus, "typeA", "custom");
        try {
            return "ok";
        } finally {
            RequestEvents.finish("get", 1l, bus, "typeA", "custom");
        }
     * }
     * </pre>
     *
     * @param query Completed query
     * @param correlationId Identifier
     * @param bus EventBus to post events to
     * @param labels Query labels to post to event bus
     */
    public static <T> void start(T query, String correlationId, EventBus bus, String... labels) {

        for (String label : labels) {
            AddLabelledQuery<T> next = start(query, correlationId, label, null);
            bus.post(next);
        }

    }

    /**
     *  Marks the start of a query identified by the provided correlationId, with additional query type and data parameters
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @param label - allows queries to be grouped by label
     * @return Start event to pass to the Events systems EventBus
     */
    public static <T> AddLabelledQuery<T> start(T query, String correlationId, String label) {
        return start(query, correlationId, label, null);
    }

    /**
     *  Marks the start of a query identified by the provided correlationId, with additional query type and data parameters
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @param label - allows queries to be grouped by label
     * @param additionalData - Any additional info about the request to be rendered in the JSON view / rest endpoint
     * @return Start event to pass to the Events systems EventBus
     */
    public static <T> AddLabelledQuery<T> start(T query, String correlationId, String label, Object additionalData) {

        return new AddLabelledQuery(
                            RequestData.builder()
                                       .query(query)
                                       .correlationId(correlationId)
                                       .type(label)
                                       .additionalData(additionalData)
                                       .build());
    }

    /**
     * Publish finish events for each of the specified query labels
     *
     * <pre>
     * {@code
     * LabelledEvents.start("get", 1l, bus, "typeA", "custom");
       try {
            return "ok";
        } finally {
            RequestEvents.finish("get", 1l, bus, "typeA", "custom");
        }
     *
     * }
     * </pre>
     *
     *
     * @param query Completed query
     * @param correlationId Identifier
     * @param bus EventBus to post events to
     * @param labels Query types to post to event bus
     */
    public static <T> void finish(T query, String correlationId, EventBus bus, String... labels) {
        for (String type : labels) {
            RemoveLabelledQuery<T> next = finish(query, correlationId, type);
            bus.post(next);
        }
    }
    /**
     * Marks the end of a query identified by the provided correlationId
     * 
     * @param query - Query data
     * @param correlationId - Identifier
     * @param label - allows queries to be grouped by type
     * @return RemoveLabelledQuery event to pass to the Events systems EventBus
     */
    public static <T> RemoveLabelledQuery<T> finish(T query, String correlationId, String label) {

        return new RemoveLabelledQuery<>(
                                 RequestData.builder()
                                            .query(query)
                                            .correlationId(correlationId)
                                            .type(label)
                                            .build());
    }
}
