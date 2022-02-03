package course.hibernate.init;

import course.hibernate.entity.Contact;
import course.hibernate.entity.Gender;
import course.hibernate.entity.Name;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;

//@Component
public class DataInitJpa implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create an EMF for our Contacts persistence-unit.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Contacts");
        EntityManager em = emf.createEntityManager();

        // Persist entity
        Contact contact = new Contact(1,
                new Name("Ivan", "Dimitrov", "Petrov"), Gender.MALE,
                "From work", new URL("http://ivan.petrov.me/"), true);
        em.getTransaction().begin();
        em.persist(contact);
        em.getTransaction().commit();

        // Cleanup
        em.close();
        emf.close();
    }
}
