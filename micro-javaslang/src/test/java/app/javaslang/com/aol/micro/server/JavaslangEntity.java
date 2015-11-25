package app.javaslang.com.aol.micro.server;

import javaslang.control.Option;

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
public class JavaslangEntity {

	private final Option<String> name;
	
	public JavaslangEntity(){
		name = Option.none();
	}
}
