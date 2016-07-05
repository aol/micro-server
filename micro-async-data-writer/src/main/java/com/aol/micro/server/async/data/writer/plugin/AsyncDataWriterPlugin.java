package com.aol.micro.server.async.data.writer.plugin;

import java.util.Set;

import com.aol.cyclops.data.collections.extensions.standard.SetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.async.data.cleaner.ConfigureDataWriter;
import com.aol.micro.server.async.data.cleaner.ConfigureScheduling;

public class AsyncDataWriterPlugin implements Plugin {

	@Override
	public Set<Class> springClasses() {
		return SetX.of(ConfigureScheduling.class, ConfigureDataWriter.class);
	}

}
