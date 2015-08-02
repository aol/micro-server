package com.aol.micro.server.auto.discovery;

import java.util.Optional;
import java.util.function.Function;

import com.aol.cyclops.trycatch.Failure;
import com.aol.cyclops.trycatch.Success;
import com.aol.cyclops.trycatch.Try;
import com.aol.micro.server.reactive.MicroLazyReact;
import com.aol.micro.server.reactive.Pipes;
import com.aol.simple.react.async.Adapter;
import com.aol.simple.react.stream.lazy.LazyReact;
import com.aol.simple.react.stream.traits.LazyFutureStream;
import com.aol.simple.react.threads.ParallelElasticPools;
import com.aol.simple.react.threads.SequentialElasticPools;


public interface RestResource {

	/**
	 * @return true if singleton
	 */
	default boolean isSingleton(){
		return false;
	}
	
}
