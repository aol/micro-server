package app.jdbc.roma.spring.data.com.aol.micro.server;

import javax.xml.bind.annotation.XmlTransient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import org.springframework.data.domain.Persistable;
import org.springframework.jdbc.roma.config.provider.annotation.RowMapperField;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JdbcEntity implements java.io.Serializable, Persistable<Long> {

	private static final long serialVersionUID = 1L;

	@RowMapperField(columnName = "id")
	@Getter
	private Long id;
	@RowMapperField(columnName = "name")
	@Getter
	private String name;
	@RowMapperField(columnName = "value")
	@Getter
	private String value;
	@RowMapperField(columnName = "version")
	@Getter
	private int version;
	
	@Setter
	private volatile boolean persisted =false;
	
	

	@Override
	@XmlTransient
	public boolean isNew() {
		return !persisted;
	}

}
