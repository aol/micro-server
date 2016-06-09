package com.aol.micro.server.s3;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CleanupFileVisitor extends SimpleFileVisitor<Path> {

	private final Path tempDirectory;
	
	public CleanupFileVisitor(Path directory) {
		this.tempDirectory = directory;
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	    deleteNotTopDirectory(file);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
		if (e == null) {
			deleteNotTopDirectory(dir);
			return FileVisitResult.CONTINUE;
		} else {
			throw e;
		}
	}
	
	private void deleteNotTopDirectory(Path dir) throws IOException {
	    if(!dir.equals(tempDirectory)) {
	        Files.delete(dir);
	    }
	}
	
}
