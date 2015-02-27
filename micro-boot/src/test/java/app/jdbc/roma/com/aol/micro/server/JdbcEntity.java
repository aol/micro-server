package app.jdbc.roma.com.aol.micro.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import org.springframework.jdbc.roma.config.provider.annotation.RowMapperField;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JdbcEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@RowMapperField(columnName = "id")
	private Long id;
	@RowMapperField(columnName = "name")
	private String name;
	@RowMapperField(columnName = "value")
	private String value;
	@RowMapperField(columnName = "version")
	private int version;

}
