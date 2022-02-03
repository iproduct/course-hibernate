package course.hibernate.init;

import course.hibernate.entity.Contact;
import course.hibernate.entity.Gender;
import course.hibernate.entity.Name;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.net.URL;
import java.util.List;

@Component
@Slf4j
public class DataInitJpaSpringBeans implements ApplicationRunner {
    //    @PersistenceUnit
//    private EntityManagerFactory emf;
    @PersistenceContext
    private EntityManager em;

    private final ApplicationEventPublisher applicationEventPublisher;
    // single TransactionTemplate shared amongst all methods in this instance
//    private final TransactionTemplate transactionTemplate;
//    // single PlatformTransactionManager shared amongst all methods in this instance
//    private final PlatformTransactionManager transactionManager;


    @Autowired
    public DataInitJpaSpringBeans(
            ApplicationEventPublisher applicationEventPublisher,
            PlatformTransactionManager transactionManager,
            TransactionTemplate transactionTemplate) {
        this.applicationEventPublisher = applicationEventPublisher;
//        this.transactionTemplate = transactionTemplate;
//        this.transactionManager = transactionManager;

    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
//         Create an EMF for our Contacts persistence-unit - user managed transaction
//        EntityManager em = emf.createEntityManager();

        // Persist entity
        List<Contact> contacts = List.of(
                new Contact(1,
                        new Name("Ivan", "Dimitrov", "Petrov"), Gender.MALE,
                        "From work", new URL("http://ivan.petrov.me/"), true),
                new Contact(2,
                        new Name("Maria", "Dimitrova", "Hristova"), Gender.FEMALE,
                        "Friend contact", new URL("http://maria.dimitrova.me/"), true));

        contacts.forEach(contact -> {
            log.info("Creating Contacts:{} - {}", contact.getId(), contact);
            try {
                em.persist(contact);
            } catch (ConstraintViolationException cve) {
                em.getTransaction().setRollbackOnly();
                log.error("Error creating contact:", cve);
                throw cve;
            }
        });

//        em.getTransaction().begin();
//        contacts.forEach(contact -> {
//            log.info("Creating Contacts:{} - {}", contact.getId(), contact);
//            em.persist(contact);
//        });
//        em.getTransaction().commit();
//        em.close();

////         excute in transaction template
//        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
//        transactionTemplate.setTimeout(5);
//        List<Contact> created = transactionTemplate.execute(status ->
//            contacts.stream().map(contact -> {
//                log.info("Creating Contacts:{} - {}", contact.getId(), contact);
//                try {
//                    em.persist(contact);
//                    return contact;
//                } catch(ConstraintViolationException cve) {
//                    status.setRollbackOnly();
//                    log.error("Error creating contact:", cve);
//                    throw cve;
//                }
//            }).collect(Collectors.toList())
//        );

//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        // explicitly setting the transaction name is something that can only be done programmatically
//        def.setName("createUsersBatchTransaction");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        def.setTimeout(5); // 5 seconds
//
//        // Do in transaction
//        TransactionStatus status = transactionManager.getTransaction(def);
//
//        List<Contact> created = contacts.stream().map(contact -> {
//            try {
//                applicationEventPublisher.publishEvent(new ContactCreationEvent(contact));
//                em.persist(contact);
//                return contact;
//            } catch (ConstraintViolationException cve) {
//                transactionManager.rollback(status);
//                log.error("Error creating contact:", cve);
//                throw cve;
//            }
//        }).collect(Collectors.toList());
//        transactionManager.commit(status); // COMMIT
//        log.info("Created Contacts: {}", created);
    }
}
