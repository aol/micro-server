package com.oath.micro.server.transactions;

import com.oath.micro.server.Plugin;
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
