package app.jackson.includes.all.com.aol.micro.server.copy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Value;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "myentity")
public class MyEntity {

	@XmlElement(name="primitive")
	Integer i = null;

	public MyEntity(int i) {
		super();
		this.i = i;
	}
	public MyEntity(){
		this.i=null;
	}
}
