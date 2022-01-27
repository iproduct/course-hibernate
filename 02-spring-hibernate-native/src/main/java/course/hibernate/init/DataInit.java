package course.hibernate.init;

import course.hibernate.entity.Contact;
import course.hibernate.entity.Name;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

//@Component
public class DataInit implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // create hibernate config
        Configuration cfg = new Configuration();
        cfg.configure();

        // Create SessionFactory
        // Create Session
        try(SessionFactory sf = cfg.buildSessionFactory();
            Session session = sf.openSession()) {

            // Persist entity
            Contact contact = new Contact(1,
                    new Name("Ivan", "Dimitrov", "Petrov"),
                    "From work", new URL("http://ivan.petrov.me/"), true);
            session.beginTransaction();
            session.persist(contact);
            session.getTransaction().commit();
        }
//        session.close();
//        sf.close();
    }
}
