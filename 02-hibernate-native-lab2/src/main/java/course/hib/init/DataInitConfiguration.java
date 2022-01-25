package course.hib.init;

import course.hib.entity.Contact;
import course.hib.entity.Name;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@Slf4j
public class DataInitConfiguration implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // Create Configuration
        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(Contact.class);
        cfg.configure();

        // Create SessionFactory
        // Create Session
        try (
                SessionFactory sf = cfg.buildSessionFactory();
                Session session = sf.openSession()) {

            // Persist Entity
            Contact contact = new Contact(1,
                    new Name("Ivan", "Dimitrov", "Petrov"),
                    "From work", new URL("http://ivan.petrov.me/"), true);
            log.info("Creating Contact:{} - {}", contact.getId(), contact);
            session.beginTransaction();
            session.persist(contact);
            session.getTransaction().commit();
        }
//        session.close();
//        sf.close();

    }
}
