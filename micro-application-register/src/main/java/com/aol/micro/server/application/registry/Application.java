package com.aol.micro.server.application.registry;

import java.util.Iterator;
import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.pcollections.ConsPStack;
import org.pcollections.PStack;

import com.aol.micro.server.rest.jackson.JacksonUtil;

@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class Application implements Iterable<RegisterEntry>{
	
	
	PStack<RegisterEntry> entries;

	public Application(final List<RegisterEntry> entries) {
		this.entries = ConsPStack.from(entries);
	}

	@Override
	public Iterator<RegisterEntry> iterator() {
		return entries.iterator();
	}
	
	public String toString(){
		return JacksonUtil.serializeToJson(entries);
	}
	
	
}
