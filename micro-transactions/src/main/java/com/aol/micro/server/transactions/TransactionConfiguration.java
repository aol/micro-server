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
	public TransactionFlow transactionBean(){
		TransactionTemplate transactionTemplate = new TransactionTemplate(ptm);
		return TransactionFlow.of(transactionTemplate, Function.identity());
	}
	
	
}
