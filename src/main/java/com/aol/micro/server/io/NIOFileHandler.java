package com.aol.micro.server.io;


import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

@Builder
@Wither
@AllArgsConstructor
public class NIOFileHandler {

	private final Charset charset;
	private final CharsetEncoder encoder;
	
	public NIOFileHandler(){
		charset = Charset.forName("UTF-8");
		encoder = charset.newEncoder();
	}
	
//	ExceptionSoftener softener = ExceptionSoftener.singleton.factory.
	public CompletableFuture read(URI uri){
		CompletableFuture result = new CompletableFuture();
		ByteBuffer buffer = ByteBuffer.allocate(100);
		try {
			AsynchronousFileChannel.open(Paths.get(uri)).read(buffer, 0,null, new CompletionHandler<Integer,Void>(){

				@Override
				public void completed(Integer r, Void attachment) {
					
					result.complete(r);
				}

				@Override
				public void failed(Throwable exc, Void attachment) {
					 result.completeExceptionally(exc);
					
				}
				
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	public CompletableFuture write(URI uri, String data){
		CompletableFuture result = new CompletableFuture();
		ByteBuffer buffer;
		try {
			buffer = encoder.encode(CharBuffer.wrap(data));
		} catch (CharacterCodingException e1) {
			throw new RuntimeException(e1);
		}
		try {
			AsynchronousFileChannel.open(Paths.get(uri)).write(buffer, 0,null, new CompletionHandler<Integer,Void>(){

				@Override
				public void completed(Integer r, Void attachment) {
					
					result.complete(r);
				}

				@Override
				public void failed(Throwable exc, Void attachment) {
					 result.completeExceptionally(exc);
					
				}
				
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
}
