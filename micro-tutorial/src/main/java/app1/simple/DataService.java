package app1.simple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.datasource.hibernate.GenericHibernateService;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableList;
import com.googlecode.genericdao.search.Search;

@Component
public class DataService {

	private final GenericHibernateService<Entity, Long> dao;

	@Autowired
	public DataService(DAOProvider<Entity, Long> daoProvider) {

		dao = daoProvider.get(Entity.class);
	}
	@Timed
	public void createEntity(String name, String value) {

		dao.save(new Entity(name, value));
	}

	@Timed
	public ImmutableList<Entity> findAll(String name){
		return ImmutableList.copyOf(searchByName(name));
		
	}

	private List<Entity> searchByName(String name) {
		return dao.<Entity>search(new Search()
				.addFilter(dao.getFilterFromExample(new Entity(name))));
	}

}
