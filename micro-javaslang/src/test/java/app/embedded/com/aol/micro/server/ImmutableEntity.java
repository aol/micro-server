package app.embedded.com.aol.micro.server;

import javaslang.collection.List;

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
public class ImmutableEntity {

	private final String value;
	private final List<String> list;
	
	public ImmutableEntity() {
		this(null,null);
	}
	
}
