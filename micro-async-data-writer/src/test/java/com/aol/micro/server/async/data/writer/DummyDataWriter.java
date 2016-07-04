package com.aol.micro.server.async.data.writer;

import com.aol.cyclops.control.FutureW;

import lombok.Getter;
import lombok.Setter;

public class DummyDataWriter implements DataWriter<String>{

	@Getter
	@Setter
	String data;
	@Getter
	@Setter
	int version = 0;
	@Getter
	@Setter
	boolean outofdate = false;
	
	@Override
	public FutureW<String> loadAndGet() {
		return FutureW.ofResult(data);
	}

	@Override
	public FutureW<Void> saveAndIncrement(String data) {
		this.data = data;
		version++;
		return FutureW.ofResult(null);
	}

	@Override
	public FutureW<Boolean> isOutOfDate() {
		return FutureW.ofResult(outofdate);
	}

	
}
