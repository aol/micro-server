package com.oath.micro.server.config;

import java.util.function.Supplier;

public class ConfigAccessor implements Supplier<Config> {

	public Config get(){
		return Config.get();
	}
	public static Supplier<Config> supplier(){
		return new ConfigAccessor();
	}
}
