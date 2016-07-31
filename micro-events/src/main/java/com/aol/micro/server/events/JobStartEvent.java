package com.aol.micro.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobStartEvent {

    private final long correlationId;
    private final String type;

}
