package com.aol.micro.server.application.registry;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.ErrorCode;
import com.aol.micro.server.rest.jackson.JacksonUtil;

@Component
public class Register {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegisterConfig config;

	@Autowired
	public Register(RegisterConfig config) {
		this.config = config;
	}

	public void register(final RegisterEntry entry) {

		File dir = new File(config.getOutputDir(), "" + entry.getModule());
		dir.mkdirs();

		File file = new File(dir, entry.getHostname() + ":" + entry.getModule() + ":" + entry.getUuid());
		try {
			FileUtils.writeStringToFile(file, JacksonUtil.serializeToJson(entry));
		} catch (IOException e) {
			logger.error("Error registering service to disk {}", JacksonUtil.serializeToJson(entry));
		}
	}
}
