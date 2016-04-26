package com.aol.micro.server.s3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Test;

public class DirectoryCleanerTest {

	@Test 
	public void clean() throws IOException {
		
		
		Path dir = Files.createTempDirectory("test");
		DirectoryCleaner cleaner = new DirectoryCleaner(dir.toString(), true);
		Path file  = Files.createTempFile(dir, "a", "b");
		Assert.assertTrue(Files.exists(file));
		cleaner.clean();
		Assert.assertFalse(Files.exists(file));

	}
}
