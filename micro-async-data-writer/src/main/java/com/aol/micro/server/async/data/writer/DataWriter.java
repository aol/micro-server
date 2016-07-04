package com.aol.micro.server.async.data.writer;

import java.util.concurrent.Executor;

import com.aol.cyclops.control.FutureW;
import com.aol.micro.server.manifest.ManifestComparator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DataWriter<T> {

	private final Executor executorService;
	private final ManifestComparator<T> comparator;
	
	public FutureW<T> loadAndGet(){
		return FutureW.ofSupplier(()->{
			comparator.load();
			return comparator.getData();
		},executorService);
	}
	
	public FutureW<Void> saveAndIncrement(T data){
		return FutureW.ofSupplier(()-> {
			comparator.saveAndIncrement(data);
			return null;
		},executorService);
	}
	public FutureW<Boolean> isOutOfDate(){
		return FutureW.ofSupplier(()->comparator.isOutOfDate(),executorService);
	}
}
