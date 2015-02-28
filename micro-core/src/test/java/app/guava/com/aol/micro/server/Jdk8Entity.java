package app.guava.com.aol.micro.server;

import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "optional")
@Getter
@AllArgsConstructor
@Builder
public class Jdk8Entity {

	private final Optional<String> name;
	
	public Jdk8Entity(){
		name = Optional.empty();
	}
}
