package course.hibernate.init;

import course.hibernate.entity.Contact;
import course.hibernate.entity.Name;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.net.URL;

@Component
@Slf4j
public class DataInitJpaPersistenceUnit implements ApplicationRunner {
//    @PersistenceUnit
//    private EntityManagerFactory emf;
    @PersistenceContext
    private EntityManager em;

    private final ApplicationEventPublisher applicationEventPublisher;
    // single PlatformTransactionManager shared amongst all methods in this instance
    private final PlatformTransactionManager transactionManager;
    // single TransactionTemplate shared amongst all methods in this instance
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public DataInitJpaPersistenceUnit(
            ApplicationEventPublisher applicationEventPublisher,
            PlatformTransactionManager transactionManager,
            TransactionTemplate transactionTemplate) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.transactionManager = transactionManager;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
//    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // Create an EMF for our Contacts persistence-unit.
//        EntityManager em = emf.createEntityManager();


        // Persist entity
        Contact contact = new Contact(1,
                new Name("Ivan", "Dimitrov", "Petrov"),
                "From work", new URL("http://ivan.petrov.me/"), true);

        // excute in transaction template
//        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
//        transactionTemplate.setTimeout(5);
//        Contact created = transactionTemplate.execute(status -> {
//            em.persist(contact);
//            return contact;
//        });

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // explicitly setting the transaction name is something that can only be done programmatically
        def.setName("createUsersBatchTransaction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(5); // 5 seconds

        // Do in transaction
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            em.persist(contact);
            transactionManager.commit(status); // COMMIT
            log.info("Created Contact: {}", contact);
        } catch (ConstraintViolationException ex) {
            log.error(">>> Constraint violation inserting user: {} - {}", contact, ex.getMessage());
            transactionManager.rollback(status); // ROLLBACK
            throw ex;
        }

    }
}
