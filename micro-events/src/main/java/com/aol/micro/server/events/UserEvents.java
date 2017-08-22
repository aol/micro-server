package com.aol.micro.server.events;

import com.aol.micro.server.events.RequestTypes.AddLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RemoveLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RequestData;

import com.google.common.eventbus.EventBus;

/**
 * Factory class for creating Start and End events which are identified by user
 *
 */
public class UserEvents {


    /**
     *  Marks the start of a query identified by the provided correlationId, with additional query type and data parameters
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @param user - allows queries to be grouped by user
     * @return Start event to pass to the Events systems EventBus
     */
    public static <T> AddLabelledQuery<T> start(T query, long correlationId, String user) {
        return start(query, correlationId, user, null);
    }

    /**
     *  Marks the start of a query identified by the provided correlationId, with additional query type and data parameters
     * 
     * @param query  - Query data
     * @param correlationId - Identifier
     * @param user - allows queries to be grouped by user
     * @param additionalData - Any additional info about the request to be rendered in the JSON view / rest endpoint
     * @return Start event to pass to the Events systems EventBus
     */
    public static <T> AddLabelledQuery<T> start(T query, long correlationId, String user, Object additionalData) {

        return new AddLabelledQuery(
                            RequestData.builder()
                                       .query(query)
                                       .correlationId(correlationId)
                                       .type(user)
                                       .additionalData(additionalData)
                                       .build());
    }

    /**
     * Marks the end of a query identified by the provided correlationId
     * 
     * @param query - Query data
     * @param correlationId - Identifier
     * @param user - allows queries to be grouped by type
     * @return
     */
    public static <T> RemoveLabelledQuery<T> finish(T query, long correlationId, String user) {

        return new RemoveLabelledQuery<>(
                                 RequestData.builder()
                                            .query(query)
                                            .correlationId(correlationId)
                                            .type(user)
                                            .build());
    }
}
