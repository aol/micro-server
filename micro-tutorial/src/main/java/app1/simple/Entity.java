package app1.simple;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@javax.persistence.Entity
@Table(name = "t_jdbc", uniqueConstraints = @UniqueConstraint(columnNames = {
		"name", "value" }))
public class Entity implements java.io.Serializable {

		private static final long serialVersionUID = 1L;

		private Long id;
		private String name;
		private String value;
		private int version;

		public Entity(String name2, String value2) {
			this.name = name2;
			this.value = value2;
		}

		public Entity(String name2) {
			this.name = name2;
		}

		public Entity(){
			
		}
		
		@Version
		@Column(name = "version", nullable = false)
		public int getVersion() {
			return version;
		}

		@Id
		@GeneratedValue(strategy = IDENTITY)
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

		public void setId(Long id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		
}

