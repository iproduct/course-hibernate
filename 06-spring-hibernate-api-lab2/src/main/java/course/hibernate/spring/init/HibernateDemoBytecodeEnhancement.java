package course.hibernate.spring.init;

import course.hibernate.spring.entity.Book;
import course.hibernate.spring.entity.Person;
import course.hibernate.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static course.hibernate.spring.entity.Role.*;

@Component
@Slf4j
public class HibernateDemoBytecodeEnhancement implements ApplicationRunner {
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "Admin123&",
                    Set.of(READER, AUTHOR, ADMIN), "+1-234-5678"),
            new User("Default", "Author", "author", "Author123&",
                    Set.of(READER, AUTHOR), "+40-123-4567"),
            new User("Default", "Reader", "reader", "Reader123&",
                    Set.of(READER), "+359-123-4567")
    );
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Book b1 = new Book("Effective Java", List.of(new Person("Joshua", "Bloch",
//                LocalDate.of(1965, 8, 11))));
        template.executeWithoutResult(status -> {
            Person josh = new Person(1L, "Joshua", "Bloch",
                    LocalDate.of(1965, 8, 11));
            Book effectiveJava = new Book(1L, "Effective Java", josh, "0134685997",
                    "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming ",
                    null,
                    "Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java 6. This Jolt award-winning classic has now been thoroughly updated to take full advantage of the latest language and library features. The support in modern Java for multiple paradigms increases the need for specific best-practices advice, and this book delivers.\n" +
                            "As in previous editions, each chapter of Effective Java, Third Edition, consists of several “items,” each presented in the form of a short, stand-alone essay that provides specific advice, insight into Java platform subtleties, and updated code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.\n" +
                            "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming constructs that were added to its object-oriented roots. Many new items have been added, including a chapter devoted to lambdas and streams."
            );
            entityManager.persist(josh);
            entityManager.persist(effectiveJava);
        });

        Book book = template.execute(status -> {
            Book book2 =  entityManager.getReference(Book.class, 1L);
            String title = book2.getTitle();
            return book2;
        });
        log.info("!!! Books: {}", book.getTitle());
    }
}
