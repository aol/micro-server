package com.aol.micro.server.transactions;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;

/**
 * 
 * @author johnmcclean
 *
 */
public class TransactionsPlugin implements Plugin{
	
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(TransactionConfiguration.class);
	}

	

}
