package com.aol.micro.server.events;



import java.util.Map;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

@Builder
@Wither
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of={"correlationId"})
public class SystemData<K, V> {

	private static final Random r = new Random();
	private final Integer processed;
	private final Integer errors;
	private final Map<K, V> dataMap;
	private String correlationId;
	
	private SystemData(Integer processed, Integer errors, Map<K, V> dataMap) {
		this.processed = processed;
		this.errors = errors;
		this.dataMap = dataMap;
		this.correlationId="" + r.nextLong();
	}
}
