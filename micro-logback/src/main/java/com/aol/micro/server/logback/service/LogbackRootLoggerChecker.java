package com.aol.micro.server.logback.service;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Service
public class LogbackRootLoggerChecker {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	
	@Setter
	@Getter
	private volatile boolean active;
	
	@Setter
	@Getter
	private volatile String correctLevelStr;
	
	@Autowired
	public LogbackRootLoggerChecker(@Value("${logback.root.logger.checker.active:true}") boolean active, @Value("${logback.root.logger.checker.correct.level:INFO}") String correctlevelStr) {
		this.active = active;
		this.correctLevelStr = correctlevelStr;
	}
	
	@Scheduled(fixedRateString = "${logback.root.logger.checker.fixed.rate:5000}")
	public void check() {
		if (active) {
		
			 Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
			 Level actualLevel = root.getLevel();
			if (!actualLevel.toString().equals(correctLevelStr)) {
				root.setLevel(Level.toLevel(correctLevelStr));
				logger.warn("Logback log level {} was incorrect. Changed to {}", actualLevel.toString(), correctLevelStr);
			}
		}
	}

}
