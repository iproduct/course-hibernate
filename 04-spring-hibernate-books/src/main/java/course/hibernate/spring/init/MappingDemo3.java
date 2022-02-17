package course.hibernate.spring.init;

import course.hibernate.spring.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

//@Component
@Slf4j
public class MappingDemo3 implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            IntProperty ageProperty = new IntProperty();
            ageProperty.setId( 1L );
            ageProperty.setName( "age" );
            ageProperty.setValue( 23 );

            entityManager.persist( ageProperty );

            StringProperty nameProperty = new StringProperty();
            nameProperty.setId( 1L );
            nameProperty.setName( "name" );
            nameProperty.setValue( "John Doe" );

            entityManager.persist( nameProperty );

            PropertyHolder namePropertyHolder = new PropertyHolder();
            namePropertyHolder.setId( 1L );
            namePropertyHolder.getProperties().add( nameProperty );
            namePropertyHolder.getProperties().add( ageProperty );

            entityManager.persist( namePropertyHolder );

//            PropertyHolder namePropertyHolder2 = new PropertyHolder();
//            namePropertyHolder2.setId( 2L );
//            namePropertyHolder2.setProperty( ageProperty );
//
//            entityManager.persist( namePropertyHolder2 );
        });
        template.executeWithoutResult(status -> {
            Set<Property> props = entityManager.find(PropertyHolder.class, 1L).getProperties();
            for (Property prop : props ) {
                log.info("!!!!! Property: {} = {} ({})", prop.getName(), prop.getValue(), prop.getClass().getSimpleName());
            }
            //            Property prop2 = entityManager.find(PropertyHolder.class, 2L).getProperty();
//            log.info("!!!!! Property: {} = {} ({})", prop2.getName(), prop2.getValue(), prop2.getClass().getSimpleName());
        });
    }
}
