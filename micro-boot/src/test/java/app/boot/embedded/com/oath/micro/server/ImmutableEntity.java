package app.boot.embedded.com.oath.micro.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

import com.google.common.collect.ImmutableList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "immutable")
@Getter
@AllArgsConstructor
@Builder
public class ImmutableEntity {

	private final String value;
	private final ImmutableList<String> list;

	public ImmutableEntity() {
		this(null,null);
	}

}
