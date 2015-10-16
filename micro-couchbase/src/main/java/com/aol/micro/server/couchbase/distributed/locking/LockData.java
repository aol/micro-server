package com.aol.micro.server.couchbase.distributed.locking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

@Wither
@AllArgsConstructor
@Getter
public class LockData {

	private final String key;
	private final String value;
	private final boolean hasLock;

}
