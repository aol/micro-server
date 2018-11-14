package app.hibernate.com.oath.micro.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.RestResource;

@Component
@Path("/persistence")
public class PersistentResource implements RestResource {


    private final SessionFactory sessionFactory;


    @Autowired
    public PersistentResource(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @GET
    @Produces("text/plain")
    @Path("/create")
    public String createEntity() {

        final Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(HibernateEntity.builder()
               .name("test")
               .value("value").build());
        session.flush();
        tx.commit();
        return "ok";
    }

    @GET
    @Produces("application/json")
    @Path("/get")
    public List<HibernateEntity> get() {
        final Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(HibernateEntity.class)
                                   .add(Example.create(HibernateEntity.builder()
                                   .name("test")
                                   .build()));

        return criteria.list();

    }


}
