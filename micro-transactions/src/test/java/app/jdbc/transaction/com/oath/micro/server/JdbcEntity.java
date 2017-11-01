package app.jdbc.transaction.com.oath.micro.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;




@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JdbcEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String value;
	private int version;


}
