package app.javaslang.com.aol.micro.server;

import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.collection.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "immutable")
@Getter
@AllArgsConstructor
@Builder
public class ImmutableJavaslangEntity {

	private final String value;
	private final List<String> list;
	private final Map<String,Set> mapOfSets;
	
	
	public ImmutableJavaslangEntity() {
		this(null,null,null);
	}
	
}
