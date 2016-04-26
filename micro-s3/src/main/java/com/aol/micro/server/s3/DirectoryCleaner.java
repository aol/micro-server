package com.aol.micro.server.s3;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DirectoryCleaner {

	private final String temporaryDirectory;

	@Autowired
	public DirectoryCleaner(@Value("${s3.temp.dir:#{null}") String temporaryDirectory) {
		this.temporaryDirectory = temporaryDirectory;
	}

	@PostConstruct
	public void clean() throws IOException {
		if (temporaryDirectory != null && new File(temporaryDirectory).exists()) {
			Path directory = FileSystems.getDefault().getPath(temporaryDirectory);
			Files.walkFileTree(directory, new CleanupFileVisitor(directory));
		}
	}

}
