package com.aol.micro.server.transactions;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class TransactionConfiguration {
	@Autowired
	private PlatformTransactionManager ptm;
	@Bean
	public TransactionTemplate flowTemplate(){
		TransactionTemplate transactionTemplate = new TransactionTemplate(ptm);
		return transactionTemplate;
	}

	@Bean
	public TransactionFlow transactionBean(){
		
		return TransactionFlow.of(flowTemplate(), Function.identity());
	}
	
	
}
