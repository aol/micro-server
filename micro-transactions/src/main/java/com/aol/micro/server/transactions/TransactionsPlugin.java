package com.aol.micro.server.transactions;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

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
