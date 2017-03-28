package com.aol.micro.server.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

import com.aol.cyclops.control.AnyM;

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

	public AnyM<String> getKeyStoreType() {
		return AnyM.ofNullable(keyStoreType);
	}
	public AnyM<String> getKeyStoreProvider() {
		return AnyM.ofNullable(keyStoreProvider);
	}
	public AnyM<String> getTrustStoreType() {
		return AnyM.ofNullable(trustStoreType);
	}
	public AnyM<String> getTrustStoreProvider() {
		return AnyM.ofNullable(trustStoreProvider);
	}
	public AnyM<String> getClientAuth() {
		return AnyM.ofNullable(clientAuth);
	}
	public AnyM<String> getCiphers() {
		return AnyM.ofNullable(ciphers);
	}
	public AnyM<String> getProtocol() {
		return AnyM.ofNullable(protocol);
	}
	public AnyM<String> getTrustStoreFile() {
		return AnyM.ofNullable(trustStoreFile);
	}
	public AnyM<String> getTrustStorePass() {
		return AnyM.ofNullable(trustStorePass);
	}
}
