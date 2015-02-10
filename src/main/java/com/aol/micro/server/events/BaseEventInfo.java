package com.aol.micro.server.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;

public class BaseEventInfo implements StartedAt {

	@Getter
	private final long freeMemory = Runtime.getRuntime().freeMemory();
	@Getter
	private final long startedAt = System.currentTimeMillis();
	@XmlTransient
	private final DateFormat format = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
	private final String startedAtFormatted = format.format(startedAt);
	@Getter
	private final long processingThread =Thread.currentThread().getId();
}
