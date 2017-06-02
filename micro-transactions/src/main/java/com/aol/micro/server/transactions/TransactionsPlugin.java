package com.aol.micro.server.transactions;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

/**
 * 
 * @author johnmcclean
 *
 */
public class TransactionsPlugin implements Plugin{
	
	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(TransactionConfiguration.class);
	}

	

}
