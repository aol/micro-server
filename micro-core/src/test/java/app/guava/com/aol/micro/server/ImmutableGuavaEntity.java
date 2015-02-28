package app.guava.com.aol.micro.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "immutable")
@Getter
@AllArgsConstructor
@Builder
public class ImmutableGuavaEntity {

	private final String value;
	private final ImmutableList<String> list;
	private final ImmutableMap<String,ImmutableSet> mapOfSets;
	private final ImmutableMultimap<String,Integer> multiMap;
	
	public ImmutableGuavaEntity() {
		this(null,null,null,null);
	}
	
}
