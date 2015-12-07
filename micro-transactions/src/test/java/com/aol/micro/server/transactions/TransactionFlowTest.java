package com.aol.micro.server.transactions;

import org.junit.Test;

import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionCallback;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TransactionFlowTest {

	@Test
	public void test() {
		TransactionTemplate transactionTemplate =  new TransactionTemplate(){
			public <T> T execute(TransactionCallback<T> action) throws TransactionException {
				return action.doInTransaction(null);
			}
		};
				
		
		Integer result = TransactionFlow.of(transactionTemplate, this::load)
										.map(this::save)
										.execute(10)
										.get();
		
		assertThat(result,equalTo(-1));
	}
	
	public String load(Integer input){
		return "data";
	}
	public Integer save(String input){
		return -1;
	}

}
