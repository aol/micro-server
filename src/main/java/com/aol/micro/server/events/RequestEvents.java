package com.aol.micro.server.events;

import com.aol.micro.server.events.RequestsBeingExecuted.AddQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RemoveQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RequestData;

public class RequestEvents {

	public static <T> AddQuery start(T query, long correlationId){
		return start(query, correlationId,"default",null);
	}
	public static <T> AddQuery start(T query, long correlationId, String type, Object additionalData){
		
		return new AddQuery(RequestData.builder().query(query).correlationId(correlationId)
		.type(type).additionalData(additionalData).build());
	}
	public static <T> RemoveQuery finish(T query, long correlationId){
		return finish(query,correlationId,"default");
	}
	public static <T> RemoveQuery finish(T query, long correlationId, String type){
		
		return new RemoveQuery(RequestData.builder().query(query).correlationId(correlationId)
		.type(type).build());
	}
	
}
