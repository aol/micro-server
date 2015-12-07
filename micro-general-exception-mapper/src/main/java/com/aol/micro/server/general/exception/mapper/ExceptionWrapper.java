package com.aol.micro.server.general.exception.mapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "exception")
@XmlType(name = "")
public class ExceptionWrapper {

	@XmlElement
	final String errorCode;

	@XmlElement
	final Object message;

	public ExceptionWrapper() {
		this.errorCode = null;
		this.message = null;
	}

	public ExceptionWrapper(final String errorCode, final Object content) {
		this.errorCode = errorCode;
		this.message = content;
	}

	public Object getMessage() {
		return message;
	}

}