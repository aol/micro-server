package com.aol.micro.server.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;

@Getter
@Builder
@AllArgsConstructor
public class SSLProperties {

	private final String keyStoreFile;
	private final String keyStorePass;
	private final String trustStoreFile;
	private final String trustStorePass;
}
