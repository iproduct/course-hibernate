package course.hibernate.spring.init;

import course.hibernate.spring.entity.Book;
import course.hibernate.spring.entity.Person;
import course.hibernate.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.core.Ehcache;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.cache.jcache.internal.JCacheDomainDataRegionImpl;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.annotation.CacheKey;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static course.hibernate.spring.entity.Role.*;

@Component
@Slf4j
public class HibernateDemoBytecodeEnhancement implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
            Person martin = new Person(2L, "Martin", "Fowler",
                    LocalDate.of(1939, 4, 15));
            Book umlDistilled = new Book(2L, "Uml Distilled", martin, "9780321193681",
                    "More than 300,000 developers have benefited from past editions of UML Distilled . This third edition is the best resource for quick, no-nonsense insights into understanding and using UML 2.0 and prior versions of the UML",
                    null,
                    "Some readers will want to quickly get up to speed with the UML 2.0 and learn the essentials of the UML. Others will use this book as a handy, quick reference to the most common parts of the UML. The author delivers on both of these promises in a short, concise, and focused presentation." +
                            "This book describes all the major UML diagram types, what they're used for, and the basic notation involved in creating and deciphering them. These diagrams include class, sequence, object, package, deployment, use case, state machine, activity, communication, composite structure, component, interaction overview, and timing diagrams. The examples are clear and the explanations cut to the fundamental design logic. Includes a quick reference to the most useful parts of the UML notation and a useful summary of diagram types that were added to the UML 2.0." +
                            "If you are like most developers, you don't have time to keep up with all the new innovations in software engineering. This new edition of Fowler's classic work gets you acquainted with some of the best thinking about efficient object-oriented software design using the UML--in a convenient format that will be essential to anyone who designs software professionally."
            );

            entityManager.persist(josh);
            entityManager.persist(martin);
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

            var author2 = entityManager.find(Person.class, 2L);
            var authors = List.of(author1, author2);
            authors.forEach( a -> log.info("!!! Authors: {} -> {}", a, a.getBooks()));
        });

        template.executeWithoutResult(status -> {
            var author1 = entityManager.unwrap(Session.class).byId(Person.class)
                    .with(CacheMode.NORMAL)
                    .load(1L);

            var author2 = entityManager.find(Person.class, 2L);
            var authors = List.of(author1, author2);
            authors.forEach( a -> log.info("!!! Authors: {} -> {}", a, a.getBooks()));
        });

//        log.info(">>>>> {}", entityManager.unwrap(Session.class).getSessionFactory().getCache(EnabledCaching));
        var cachingProvider = Caching.getCachingProvider();
        CacheManager manager = cachingProvider.getCacheManager();
        var cacheNames = manager.getCacheNames();
        log.info(">>>>> {}", cacheNames);

        // Cache statistics
        var session = entityManager.unwrap(Session.class);
        var sessionFactory = session.getSessionFactory();
        sessionFactory.getStatistics().logSummary();
        System.out.println("Second level caches:");
        List<String> secondLevelCaches = List.of(sessionFactory.getStatistics().getSecondLevelCacheRegionNames());
        System.out.println(secondLevelCaches);
        secondLevelCaches.forEach(name -> {
            System.out.printf("%s -> %s%n", name,
                    sessionFactory.getStatistics().getDomainDataRegionStatistics(name));
            try {
                var ehcache = (JCacheDomainDataRegionImpl)sessionFactory.getCache().unwrap(EnabledCaching.class).getRegion(name);
//                var iter = ehcache.getEntityDataAccess(new NavigableRole(name)).iterator();
//                iter.forEachRemaining(System.out::println);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
//
//                }
//                boolean exists = list.stream().map(cacheKey -> (Long)cacheKey.getKey()).anyMatch(key-> key.equals(employeeId));
//                System.out.println("Cached entities:" + i
//
//                List<CacheKey> list = ehcache.getKeys();
//
//                        ((JCacheDomainDataRegionImpl)sessionFactory.getCache().unwrap(EnabledCaching.class).getRegion(name))
//                                .getEntityDataAccess(new NavigableRole(name)).get( 1L));
//            } catch(Exception e){
//                e.printStackTrace();
//            }
//        });
//        System.out.println(Caching.getCachingProvider().getCacheManager().getURI()); //getCache("products"));
        // Find again same entities
//        List<Book> books2 = template.execute(status -> {
//            Book book1 = entityManager.unwrap(Session.class).byNaturalId(Book.class)
//                    .using("isbn", "0134685997")
//                    .load();
//            Book book2 = entityManager.find(Book.class, 2L);
//            return List.of(book1, book2);
//        });
//        books2.forEach(book -> log.info("!!! Book: {}", book));
    }
}
