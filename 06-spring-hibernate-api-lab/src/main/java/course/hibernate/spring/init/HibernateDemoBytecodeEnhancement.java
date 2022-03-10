package course.hibernate.spring.init;

import course.hibernate.spring.entity.Book;
import course.hibernate.spring.entity.Person;
import course.hibernate.spring.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.cache.internal.EnabledCaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
@Slf4j
public class HibernateDemoBytecodeEnhancement implements ApplicationRunner {

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
            Book effectiveJava = new Book(1L, "Effective Java", List.of(josh), "0134685997", 2015,
                    "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming ",
                    null,
                    "Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java 6. This Jolt award-winning classic has now been thoroughly updated to take full advantage of the latest language and library features. The support in modern Java for multiple paradigms increases the need for specific best-practices advice, and this book delivers.\n" +
                            "As in previous editions, each chapter of Effective Java, Third Edition, consists of several “items,” each presented in the form of a short, stand-alone essay that provides specific advice, insight into Java platform subtleties, and updated code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.\n" +
                            "The third edition covers language and library features added in Java 7, 8, and 9, including the functional programming constructs that were added to its object-oriented roots. Many new items have been added, including a chapter devoted to lambdas and streams."
            );
            Person martin = new Person(2L, "Martin", "Fowler",
                    LocalDate.of(1939, 4, 15));
            Book umlDistilled = new Book(2L, "Uml Distilled", List.of(martin), "9780321193681", 2009,
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

            entityManager.persist(josh);
            entityManager.persist(martin);
            entityManager.persist(john);
            entityManager.persist(jane);
            josh.getBooks().add(effectiveJava);
            entityManager.persist(effectiveJava);
            martin.getBooks().add(umlDistilled);
            entityManager.persist(umlDistilled);
        });

        List<Book> books = template.execute(status -> {
//            Book book1 = entityManager.unwrap(Session.class).get(Book.class, 1L);
            Book book1 = entityManager.unwrap(Session.class).byId(Book.class)
                    .with(CacheMode.GET)
                    .load(1L);
//            Book book1 = entityManager.unwrap(Session.class).bySimpleNaturalId(Book.class)
//                    .load("0134685997");

            Book book2 = entityManager.find(Book.class, 2L);
//            List<Book> results = entityManager.createQuery("select b from Book b", Book.class)
//                    .getResultList();

//            try {
//                log.info("!!!!!! BOOK author: {}:", book2.getAuthor().getFirstName());
//                log.info("!!!!!! BOOK sample content: {}:", book2.getSampleContent());
//            } catch (Exception expected) {
//                log.info("!!!! Author is not set");
//            }
            return List.of(book1, book2);
        });
        books.forEach(book -> log.info("!!! Book: {}", book));

        template.executeWithoutResult(status -> {
            var author1 = entityManager.unwrap(Session.class).byId(Person.class)
                    .with(CacheMode.NORMAL)
                    .load(1L);

            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.cache.storeMode", CacheStoreMode.USE);
            props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
            var author2 = entityManager.find(Person.class, 2L, props);

            var authors = List.of(author1, author2);
            authors.forEach( a -> log.info("!!! Authors: {} -> {}", a, a.getBooks()));
        });
        cacheUtil.logCacheStatistics();

        template.executeWithoutResult(status -> {
            var author1 = entityManager.unwrap(Session.class).byId(Person.class)
                    .with(CacheMode.NORMAL)
                    .load(1L);

            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.cache.storeMode", CacheStoreMode.USE);
            props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
            var author2 = entityManager.find(Person.class, 2L, props);

            var authors = List.of(author1, author2);
            authors.forEach( a -> log.info("!!! Authors: {} -> {}", a, a.getBooks()));
        });

        // Cache statistics
        cacheUtil.logCacheStatistics();
        // Print second cache statistics
//        log.info(">>>> CACHE Person ID=1L: {}", entityManager.getEntityManagerFactory().getCache()
//                .unwrap(EnabledCaching.class).getRegion("course.hibernate.spring.entity.Person"));
//        log.info(">>>> CACHE Person ID=2L: {}", entityManager.getEntityManagerFactory().getCache()
//                .contains(Person.class, 2L));

        // Use query cache
        List<Person> persons1 = entityManager
                .createQuery("select p from Person p where p.lastName = :lastName", Person.class)
                .setParameter("lastName", "Doe")
                .setHint("org.hibernate.cacheable", "true")
                .getResultList();
        log.info(">>> First Query Results: {}", persons1);

        // Hibernate API
        List<Person> persons2 = entityManager.unwrap(Session.class)
                .createQuery("select p from Person p where p.lastName = :lastName")
                .setParameter("lastName", "Doe")
                .setCacheable(true)
                .list();
        log.info(">>> Second Query Results: {}", persons2);
        cacheUtil.logCacheStatistics();

        // Update query cache
//        template.executeWithoutResult(status -> {
//            var results = entityManager.createQuery(
//                            "update Person p set p.firstName='Updated' where p.lastName = :lastName")
//                    .setParameter("lastName", "Doe")
//                    .setHint("org.hibernate.cacheable", "true")
//                    .executeUpdate();
//            log.info(">>> Updated: {}", results);
//        });

        template.executeWithoutResult(status -> {
            var results = entityManager.unwrap(Session.class).createNativeQuery(
                            "update persons set f_name='Updated' where l_name = :lastName")
                    .setParameter("lastName", "Doe")
                    .addSynchronizedEntityClass(Person.class)
                    .executeUpdate();
            log.info(">>> Updated: {}", results);
        });

        List<Person> persons3 = entityManager.unwrap(Session.class)
                .createQuery("select p from Person p ")
//                .setParameter("lastName", "Doe")
                .setCacheable(true)
                .list();
        log.info(">>> Second Query Results: {}", persons3);
        cacheUtil.logCacheStatistics();

        // Hibernate API
        List<Person> persons4 = entityManager.unwrap(Session.class)
                .createQuery("select p from Person p where p.lastName = :lastName")
                .setParameter("lastName", "Doe")
                .setCacheable(true)
                .list();
        log.info(">>> Second Query Results: {}", persons4);
        cacheUtil.logCacheStatistics();
    }


}
