package course.hibernate.init;

import course.hibernate.entity.Contact;
import course.hibernate.entity.Gender;
import course.hibernate.entity.Name;
import course.hibernate.events.ContactCreationEvent;
import course.hibernate.utils.ExceptionHandlingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.List;

//@Component
@Slf4j
public class DataInitJpa implements ApplicationRunner {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create an EMF for our Contacts persistence-unit.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Contacts");
        EntityManager em = emf.createEntityManager();

        // Persist entity
        List<Contact> contacts = List.of(
                new Contact(1,
                        new Name("Ivan", "Dimitrov", "Petrov"), Gender.MALE,
                        "From work", new URL("http://ivan.petrov.me/"), true),
                new Contact(2,
                        new Name("Maria", "Dimitrova", "H"), Gender.FEMALE,
                        "Friend contact", new URL("http://maria.dimitrova.me/"), true));

        em.getTransaction().begin();
        try {
            contacts.forEach(contact -> {
                try {
                    em.persist(contact);
//                    log.info(">>> Created new Contact: {}", contact);
                } catch (Exception ex) {
                    log.error("Error creating Contact: " + contact + ":", ExceptionHandlingUtil.extractConstraintViolations(ex));
                    em.getTransaction().rollback();
                }
            });
            em.getTransaction().commit();
        } catch (Exception ex) {
            log.error("Error creating Contacts: {}", ExceptionHandlingUtil.extractConstraintViolations(ex));
        }

        // Cleanup
        em.close();
        emf.close();
    }
}
