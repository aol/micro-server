package com.aol.micro.server;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import com.aol.cyclops.functions.Memoise;
import com.aol.cyclops.lambda.monads.SequenceM;


public class FunctionalModuleLoader {

	public final Supplier<List<FunctionalModule>> functionalModules = Memoise.memoiseSupplier(this::load);

	public final static FunctionalModuleLoader INSTANCE = new FunctionalModuleLoader();

	private FunctionalModuleLoader(){}
	
	
	private List<FunctionalModule> load(){
		 return  SequenceM.fromIterable(ServiceLoader.load(FunctionalModule.class)).toList();
	}
}
