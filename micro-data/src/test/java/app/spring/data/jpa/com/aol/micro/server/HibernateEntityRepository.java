package app.spring.data.jpa.com.aol.micro.server;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface HibernateEntityRepository extends CrudRepository<HibernateEntity, Long> {

}
