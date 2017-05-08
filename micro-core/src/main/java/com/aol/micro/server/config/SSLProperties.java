package com.aol.micro.server.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

import java.util.Optional;


@Getter
@Builder
@AllArgsConstructor
public class SSLProperties {

	private final String keyStoreFile;
	private final String keyStorePass;
	private final String trustStoreFile;
	private final String trustStorePass;
	private final String keyStoreType;
	private final String keyStoreProvider;
	private final String trustStoreType;
	private final String trustStoreProvider;
	private final String clientAuth;
	private final String ciphers;
	private final String protocol;

	public Optional<String> getKeyStoreType() {
		return Optional.ofNullable(keyStoreType);
	}
	public Optional<String> getKeyStoreProvider() {
		return Optional.ofNullable(keyStoreProvider);
	}
	public Optional<String> getTrustStoreType() {
		return Optional.ofNullable(trustStoreType);
	}
	public Optional<String> getTrustStoreProvider() {
		return Optional.ofNullable(trustStoreProvider);
	}
	public Optional<String> getClientAuth() {
		return Optional.ofNullable(clientAuth);
	}
	public Optional<String> getCiphers() {
		return Optional.ofNullable(ciphers);
	}
	public Optional<String> getProtocol() {
		return Optional.ofNullable(protocol);
	}
	public Optional<String> getTrustStoreFile() {
		return Optional.ofNullable(trustStoreFile);
	}
	public Optional<String> getTrustStorePass() {
		return Optional.ofNullable(trustStorePass);
	}
}
