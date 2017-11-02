package com.oath.micro.server.events;

import com.oath.micro.server.events.RequestTypes.AddQuery;
import com.oath.micro.server.events.RequestTypes.RemoveQuery;
import com.oath.micro.server.events.RequestTypes.RequestData;
import com.google.common.eventbus.EventBus;

/**
 * Factory class for creating Start and End events
 * 
 * @author johnmcclean
 *
 */
public class RequestEvents {

    /**
     * Marks the start of a query identified by the provided correlationId
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @return Start event to pass to the Events systems EventBus
     */
    public static <T> AddQuery<T> start(T query, long correlationId) {
        return start(query, correlationId, "default", null);
    }

    /**
     *  Marks the start of a query identified by the provided correlationId, with additional query type and data parameters
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @param type - allows queries to be grouped by type
     * @return Start event to pass to the Events systems EventBus
     */
    public static <T> AddQuery<T> start(T query, long correlationId, String type) {
        return start(query, correlationId, type, null);
    }

    /**
     * Publish start events for each of the specified query types
     * 
     * <pre>
     * {@code 
        RequestEvents.start("get", 1l, bus, "typeA", "custom");
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
     * @param types Query types to post to event bus
     */
    public static <T> void start(T query, long correlationId, EventBus bus, String... types) {

        for (String type : types) {
            AddQuery<T> next = start(query, correlationId, type, null);
            bus.post(next);
        }

    }

    /**
     *  Marks the start of a query identified by the provided correlationId, with additional query type and data parameters
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @param type - allows queries to be grouped by type
     * @param additionalData - Any additional info about the request to be rendered in the JSON view / rest endpoint
     * @return Start event to pass to the Events systems EventBus
     */
    public static <T> AddQuery<T> start(T query, long correlationId, String type, Object additionalData) {

        return new AddQuery(
                            RequestData.builder()
                                       .query(query)
                                       .correlationId(correlationId)
                                       .type(type)
                                       .additionalData(additionalData)
                                       .build());
    }

    /**
     * Marks the end of a query identified by the provided correlationId
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @return Finish event to pass to the Events systems EventBus
     */
    public static <T> RemoveQuery<T> finish(T query, long correlationId) {
        return finish(query, correlationId, "default");
    }

    /**
     * Publish finish events for each of the specified query types
     * 
     * <pre>
     * {@code 
     * RequestEvents.start("get", 1l, bus, "typeA", "custom");
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
     * @param types Query types to post to event bus
     */
    public static <T> void finish(T query, long correlationId, EventBus bus, String... types) {
        for (String type : types) {
            RemoveQuery<T> next = finish(query, correlationId, type);
            bus.post(next);
        }
    }

    /**
     * Marks the end of a query identified by the provided correlationId
     * 
     * @param query - Query data
     * @param correlationId - Identifier
     *  @param type - allows queries to be grouped by type
     * @return
     */
    public static <T> RemoveQuery<T> finish(T query, long correlationId, String type) {

        return new RemoveQuery<>(
                                 RequestData.builder()
                                            .query(query)
                                            .correlationId(correlationId)
                                            .type(type)
                                            .build());
    }

}
