package com.aol.micro.server.application.registry;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "register-entry")
@XmlType(name = "")
@Getter
@Wither
@Builder
public class RegisterEntry {

	int port;
	String hostname;
	String module;
	String context;
	Date time;
	String uuid;
	String target;

	public RegisterEntry() {
		this(-1, null, null, null, null, null, null);
	}

	public RegisterEntry(int port, String hostname, String module, String context, Date time, 
								String uuid, String target) {
		this.port = port;
		this.hostname = hostname;
		this.module = module;
		this.context = context;
		this.time = time;
		this.uuid = uuid;
		this.target = target;
	}

	public RegisterEntry(int port, String hostname, String module, String context,
							Date time,String target) {
		this(port, hostname, module, context, time, UUID.randomUUID().toString(),target);
	}
}
