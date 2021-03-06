package course.hibernate.spring.init;

import course.hibernate.spring.entity.Book;
import course.hibernate.spring.entity.Person;
import course.hibernate.spring.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static javax.persistence.PessimisticLockScope.EXTENDED;

//@Component
@Slf4j
public class LockingDemo implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Autowired
    CacheUtil cacheUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            Person josh = new Person(1L, "Joshua", "Bloch",
                    LocalDate.of(1965, 8, 11));
            Book effectiveJava2ed = new Book(1L, "Effective Java", List.of(josh), "0134685997", 2002,
                    "The second edition covers language and library features added in Java 7, 8, and 9, including the functional programming ",
                    null,
                    "As in previous editions, each chapter of Effective Java, Second Edition, consists of several “items,” each presented in the form of a short, stand-alone essay that provides specific advice, insight into Java platform subtleties, and updated code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.\n"
            );
            Book effectiveJava3ed = new Book(2L, "Effective Java 3-nd ed.", List.of(josh), "01346852334", 2020,
                    "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming ",
                    null,
                    "Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java 6. This Jolt award-winning classic has now been thoroughly updated to take full advantage of the latest language and library features. The support in modern Java for multiple paradigms increases the need for specific best-practices advice, and this book delivers.\n" +
                            "As in previous editions, each chapter of Effective Java, Third Edition, consists of several “items,” each presented in the form of a short, stand-alone essay that provides specific advice, insight into Java platform subtleties, and updated code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.\n" +
                            "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming constructs that were added to its object-oriented roots. Many new items have been added, including a chapter devoted to lambdas and streams."
            );
            Person martin = new Person(2L, "Martin", "Fowler",
                    LocalDate.of(1939, 4, 15));
            Book umlDistilled = new Book(3L, "Uml Distilled", List.of(martin), "9780321193681", 2009,
                    "More than 300,000 developers have benefited from past editions of UML Distilled . This third edition is the best resource for quick, no-nonsense insights into understanding and using UML 2.0 and prior versions of the UML",
                    null,
                    "Some readers will want to quickly get up to speed with the UML 2.0 and learn the essentials of the UML. Others will use this book as a handy, quick reference to the most common parts of the UML. The author delivers on both of these promises in a short, concise, and focused presentation." +
                            "This book describes all the major UML diagram types, what they're used for, and the basic notation involved in creating and deciphering them. These diagrams include class, sequence, object, package, deployment, use case, state machine, activity, communication, composite structure, component, interaction overview, and timing diagrams. The examples are clear and the explanations cut to the fundamental design logic. Includes a quick reference to the most useful parts of the UML notation and a useful summary of diagram types that were added to the UML 2.0." +
                            "If you are like most developers, you don't have time to keep up with all the new innovations in software engineering. This new edition of Fowler's classic work gets you acquainted with some of the best thinking about efficient object-oriented software design using the UML--in a convenient format that will be essential to anyone who designs software professionally."
            );
            Person john = new Person(3L, "John", "Doe",
                    LocalDate.of(1978, 8, 11));
            Person jane = new Person(4L, "Jane", "Doe",
                    LocalDate.of(1981, 8, 11));

            log.info(">>>Joshua - version before persist: {}", josh.getVersion());
            entityManager.persist(josh);
            log.info(">>>Joshua - version after persist: {}", josh.getVersion());
            entityManager.persist(martin);
            entityManager.persist(john);
            entityManager.persist(jane);
            josh.getBooks().add(effectiveJava2ed);
            entityManager.persist(effectiveJava2ed);
            josh.getBooks().add(effectiveJava3ed);
            entityManager.persist(effectiveJava3ed);
            martin.getBooks().add(umlDistilled);
            entityManager.persist(umlDistilled);
        });

        template.executeWithoutResult(status -> {
            var josh = entityManager.find(Person.class, 1L);
            log.info(">>>Joshua - version in first transaction before lock: {}", josh.getVersion());
            entityManager.lock(josh, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            var results = entityManager.unwrap(Session.class).createQuery(
                            "select p from Person p where p.lastName = :lastName")
                    .setParameter("lastName", "Bloch")
                    .setHint("org.hibernate.cacheable", "true")
//                    .setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
                    .setLockOptions(new LockOptions(LockMode.PESSIMISTIC_FORCE_INCREMENT)
                            .setFollowOnLocking(false)
                            .setTimeOut(2000)
                            .setScope(true))
                    .getResultList();
            log.info(">>> Updated: {}", results);
            log.info(">>>Joshua - in first transaction: {}", josh.getVersion());
        });

        template.executeWithoutResult(status -> {
            var josh = entityManager.find(Person.class, 1L);
            log.info(">>>Joshua - version in second transaction before update: {}", josh.getVersion());
            var results = entityManager.unwrap(Session.class).createQuery(
                            "update versioned Person p set p.firstName='Updated' where p.lastName = :lastName")
                    .setParameter("lastName", "Bloch")
                    .setHint("org.hibernate.cacheable", "true")
                    .executeUpdate();
            log.info("Updated: {} records", results);
            entityManager.refresh(josh);
            log.info(">>>Joshua - version in second transactio after update: {}", josh.getVersion());
        });

        template.executeWithoutResult(status -> {
            var josh = entityManager.find(Person.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            log.info(">>>Joshua - version in third transaction: {}", josh.getVersion());
        });
        template.executeWithoutResult(status -> {
            var josh = entityManager.find(Person.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            log.info(">>>Joshua - version in forth transaction: {}", josh.getVersion());
        });
        entityManager.createQuery("select p from Person p").getSingleResult();
    }


}













