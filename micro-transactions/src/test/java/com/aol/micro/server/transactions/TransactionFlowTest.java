package com.aol.micro.server.transactions;

import org.junit.Test;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionCallback;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TransactionFlowTest {
	TransactionTemplate transactionTemplate =  new TransactionTemplate(){
		public <T> T execute(TransactionCallback<T> action) throws TransactionException {
			return action.doInTransaction(null);
		}
	};
	TransactionTemplate transactionTemplate2 =  new TransactionTemplate(){
		public <T> T execute(TransactionCallback<T> action) throws TransactionException {
			return action.doInTransaction(null);
		}
	};
	@Test
	public void test() {
		
				
		
		Integer result = TransactionFlow.<Integer>of(transactionTemplate)
										.map(this::load)
										.map(this::save)
										.execute(10)
										.orElse(-1);
		
		assertThat(result,equalTo(-1));
	}
	
	@Test
	public void flatMapTest(){
		String result = TransactionFlow.of(transactionTemplate, this::load)
										.flatMap(this::newTransaction)
										.execute(10)
										.orElse("");
		
	}
	
	@Test
	public void errorHandlingGeneral() {
		
				
		
		Throwable result = TransactionFlow.<Integer>of(transactionTemplate)
										.map(this::load)
										.map(this::error)
										.execute(10)
										.toFailedOptional()
										.get();
		
		
		assertThat(result,instanceOf(RuntimeException.class));
	}
	@Test(expected=RuntimeException.class)
	public void errorHandlingSpecific() {
		
				
		
		Throwable result = TransactionFlow.<Integer>of(transactionTemplate)
										.map(this::load)
										.map(this::error2)
										.execute(10,IllegalArgumentException.class)
										.toFailedOptional()
										.get();
		
		
		fail("exception expected!");
	}
	@Test
	public void errorHandlingSpecificCaught() {
		
				
		
		NullPointerException result = TransactionFlow.<Integer>of(transactionTemplate)
										.map(this::load)
										.map(this::error2)
										.execute(10,NullPointerException.class)
										.toFailedOptional()
										.get();
		
		
		assertThat(result,instanceOf(NullPointerException.class));
	}
	public TransactionFlow<Integer,String> newTransaction(String input){
		return TransactionFlow.of(transactionTemplate2, in -> input+":"+in);
	}
	
	public String load(Integer input){
		return "data";
	}
	public Integer save(String input){
		return -1;
	}
	public Integer error(String input){
		throw new RuntimeException();
	}
	public Integer error2(String input){
		throw new NullPointerException();
	}

}
