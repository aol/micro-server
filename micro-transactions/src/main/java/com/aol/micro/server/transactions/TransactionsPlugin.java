package com.aol.micro.server.transactions;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

/**
 * 
 * @author johnmcclean
 *
 */
public class TransactionsPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses() {
		return SetX.of(TransactionConfiguration.class);
	}

	

}
