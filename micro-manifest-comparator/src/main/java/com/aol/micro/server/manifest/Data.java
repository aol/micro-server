package com.aol.micro.server.manifest;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

public class Data<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Getter
	private final T data;
	@Getter
	private final Date date;
	@Getter
	private  final String versionedKey;
	
	public Data(T data, Date date, String versionedKey){
		this.data = data;
		this.date = date;
		this.versionedKey = versionedKey;
	}
}
