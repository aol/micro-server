package app.jdbc.roma.spring.data.com.aol.micro.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.roma.service.RowMapperService;
import org.springframework.stereotype.Repository;

import com.aol.micro.server.utility.HashMapBuilder;
import com.nurkiewicz.jdbcrepository.JdbcRepository;
import com.nurkiewicz.jdbcrepository.RowUnmapper;

@Repository
public class JdbcEntityRepository extends JdbcRepository<JdbcEntity, Long> {
	
	private final static RowUnmapper<JdbcEntity> unmapper = (obj) ->(HashMapBuilder.<String,Object>of("name",obj.getName(),
																				"value",obj.getValue(),"id",obj.getId(),
																				"version",obj.getVersion())).build(); 
	
	@Autowired 
	public JdbcEntityRepository(RowMapperService service) {
        super(service.getRowMapper(JdbcEntity.class),unmapper, "t_table");
      
    }
	
}
