package com.aol.micro.server.log4j.service;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Log4jRootLoggerChecker {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	
	@Setter
	@Getter
	private volatile boolean active;
	
	@Setter
	@Getter
	private volatile String correctLevelStr;
	
	@Autowired
	public Log4jRootLoggerChecker(@Value("${log4j.root.logger.checker.active:true}") boolean active, @Value("${log4j.root.logger.checker.correct.level:INFO}") String correctlevelStr) {
		this.active = active;
		this.correctLevelStr = correctlevelStr;
	}
	
	@Scheduled(fixedRateString = "${log4j.root.logger.checker.fixed.rate:5000}")
	public void check() {
		if (active) {
			Level actualLevel = Logger.getRootLogger().getLevel();
			if (!actualLevel.toString().equals(correctLevelStr)) {
				Logger.getRootLogger().setLevel(Level.toLevel(correctLevelStr));
				logger.warn("Log4j log level {} was incorrect. Changed to {}", actualLevel.toString(), correctLevelStr);
			}
		}
	}

}
