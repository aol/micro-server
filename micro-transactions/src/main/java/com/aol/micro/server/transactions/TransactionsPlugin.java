package com.aol.micro.server.transactions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;

/**
 * 
 * @author johnmcclean
 *
 */
public class TransactionsPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(TransactionConfiguration.class));
	}

	

}
