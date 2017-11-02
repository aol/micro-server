package com.oath.micro.server.events;

import java.util.Map;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;
import lombok.experimental.Wither;

@Builder
@Wither
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = { "correlationId" })
public class SystemData<K, V> {

    private static final Random r = new Random();
    private final long processed;
    private final long errors;
    private final Map<K, V> dataMap;
    private String correlationId;

    private SystemData(long processed, long errors, Map<K, V> dataMap) {
        this.processed = processed;
        this.errors = errors;
        this.dataMap = dataMap;
        this.correlationId = "" + r.nextLong();

    }
}
