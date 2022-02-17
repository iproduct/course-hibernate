package course.hibernate.spring.init;

import course.hibernate.spring.entity.City;
import course.hibernate.spring.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Component
@Slf4j
public class MappingDemo2 implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            City _NewYork = new City();
            _NewYork.setName( "New York" );
            entityManager.persist( _NewYork );

            Person person = new Person();
            person.setId( 1L );
            person.setName( "John Doe" );
            person.setCityName( "Atlantis" );
            entityManager.persist( person );
        });

        template.executeWithoutResult(status -> {
            Person found = entityManager.find(Person.class, 1L);
            log.info("!!! Person from city :{}", found.getCity());
        });
    }
}
