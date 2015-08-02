package app1.simple;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableList;

@Component
public class DataService {

	private final SessionFactory sf;

	@Autowired
	public DataService(SessionFactory factory) {

		sf = factory; 
	}
	@Timed
	public void createEntity(String name, String value) {

		final Session session = sf.openSession();
		session.save(new Entity(name, value));
		session.flush();
	}

	@Timed
	public ImmutableList<Entity> findAll(String name){
		return ImmutableList.copyOf(searchByName(name));
		
	}

	private List<Entity> searchByName(String name) {
		final Session session = sf.openSession();
		
		Criteria criteria = session.createCriteria(Entity.class)
								.add(Example.create(new Entity(name)));
		
		return criteria.list();
		
	}

}
