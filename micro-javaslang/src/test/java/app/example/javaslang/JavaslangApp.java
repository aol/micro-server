package app.example.javaslang;

import java.util.Arrays;

import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.collection.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.experimental.Builder;
import lombok.Getter;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.config.Microserver;

@Microserver
@Path("/javaslang")
@Rest
public class JavaslangApp {


	public static void main(String[] args){
		 new MicroserverApp(() -> "javaslang-app").start();

	}

	
	@GET
	@Produces("application/json")
	@Path("/ping")
	public ImmutableJavaslangEntity ping() {
		return ImmutableJavaslangEntity.builder().value("value")
				.list(List.of("hello", "world"))
				.mapOfSets(HashMap.<String,Set>empty().put("key1",HashSet.ofAll(Arrays.asList(1, 2, 3))))
				.build();
	}
	
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	@XmlRootElement(name = "immutable")
	@Getter
	@AllArgsConstructor
	@Builder
	public static class ImmutableJavaslangEntity {

		private final String value;
		private final List<String> list;
		private final Map<String,Set> mapOfSets;
		
		
		public ImmutableJavaslangEntity() {
			this(null,null,null);
		}
		
	}

}    
