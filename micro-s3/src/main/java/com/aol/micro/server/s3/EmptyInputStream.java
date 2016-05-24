package com.aol.micro.server.s3;

import java.io.IOException;
import java.io.InputStream;

class EmptyInputStream extends InputStream {

	@Override
	public int available() {
		return 0;
	}
	
	@Override
	public int read() throws IOException {
		throw new IOException("Nothing to read here");
	}

}
