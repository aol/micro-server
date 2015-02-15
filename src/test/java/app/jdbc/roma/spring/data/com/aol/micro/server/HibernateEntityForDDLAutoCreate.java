package app.jdbc.roma.spring.data.com.aol.micro.server;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

//class is here only for ddl auto-creation

@Entity
@Table(name = "t_table", uniqueConstraints = @UniqueConstraint(columnNames = {
		"name", "value" }))
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HibernateEntityForDDLAutoCreate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String value;
	private int version;

	@Version
	@Column(name = "version", nullable = false)
	public int getVersion() {
		return version;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	@Column(name = "value", nullable = false)
	public String getValue() {
		return value;
	}

}
