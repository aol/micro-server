package com.aol.micro.server.events;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;

import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


public class RequestsBeingExecuted<T> {

	private final EventBus bus;
	private final ActiveEvents<RequestData<T>> events = new ActiveEvents();
	@Getter
	private final String type;
	
	
	public RequestsBeingExecuted(@Qualifier("microserverEventBus") EventBus bus,  boolean queryCapture){
		this.bus = bus;
		this.type = "default";
		if(queryCapture)
			bus.register(this);
		
	}
	
	public RequestsBeingExecuted(EventBus bus, String type){
		this.bus = bus;
		this.type = type;
		bus.register(this);
		
	}
	
	public int events(){
		return events.events();
	}
	
	public int size(){
		return events.size();
	}
	
	public String toString(){
		return events.toString();
	}
	@Subscribe
	public void finished(RemoveQuery<T> data) {
		if(type.equals(data.getData().type))
			events.finished(buildId(data.getData()));
 
	}
	@Subscribe
	public void processing(AddQuery<T> data) {
		if(type.equals(data.getData().type)){
			String id = buildId(data.getData());
			events.active(id, data.getData());
		}
		
	}
	

	private String buildId(RequestData data) {
		String id =  ""+data.correlationId;
		return id;
	}
	
	
	public static class AddQuery<T> extends AddEvent<RequestData<T>>{

		public AddQuery(RequestData<T> data) {
			super(data);
		}
		
	}
	
	public static class RemoveQuery<T> extends RemoveEvent<RequestData<T>>{

		public RemoveQuery(RequestData data) {
			super(data);
		}
		
	}

	
	@AllArgsConstructor
	@Builder
	@XmlAccessorType(XmlAccessType.FIELD)
	static class RequestData<T>  extends BaseEventInfo {

		private final long correlationId;
		
		private final T query;
		
		private final String type;
		private final Object additionalData;
	}

}
