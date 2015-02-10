package com.aol.micro.server.events;

import java.util.Map;

import lombok.Getter;
import lombok.experimental.Builder;

@Builder
@Getter
public class SystemData<K, V> {

	private final Integer processed;
	private final Integer errors;
	private final Map<K, V> dataMap;

	private SystemData(Integer processed, Integer errors, Map<K, V> dataMap) {
		this.processed = processed;
		this.errors = errors;
		this.dataMap = dataMap;
	}
}
