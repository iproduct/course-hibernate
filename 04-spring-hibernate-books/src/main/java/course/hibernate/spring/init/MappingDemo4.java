package course.hibernate.spring.init;

import course.hibernate.spring.entity.Country;
import course.hibernate.spring.entity.Role;
import course.hibernate.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MappingDemo4 implements ApplicationRunner {
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "Admin123&",
                    Set.of(Role.READER, Role.AUTHOR, Role.ADMIN), "+1-234-5678"),
            new User("Default", "Author", "author", "Author123&",
                    Set.of(Role.READER, Role.AUTHOR), "+40-123-4567"),
            new User("Default", "Reader", "reader", "Reader123&",
                    Set.of(Role.READER), "+359-123-4567")
    );

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Country> contries = List.of(
                new Country(1, "United States"),
                new Country(40, "Romania"),
                new Country(359, "Bulgaria")
        );

        template.executeWithoutResult(status -> {
            List<Country> results = contries.stream().map(c -> {
                entityManager.persist(c);
                return c;
            }).collect(Collectors.toList());
            log.info("!!!!! Contries: {}", results);
        });

        List<User> users = template.execute(status -> {
            List<User> results = SAMPLE_USERS.stream().map(u -> {
                entityManager.persist(u);
                return u;
            }).collect(Collectors.toList());
            log.info("!!!!! Users: {}", results);
            return results;
        });

        template.executeWithoutResult(status -> {
            log.info("!!!!! Fetching USERS:");
            User u = entityManager.find(User.class, users.get(0).getId());
            log.info("!!!!! User: {} -> {}", u, u.getCountry());
            User u2 = entityManager.find(User.class, users.get(1).getId());
            log.info("!!!!! User: {} -> {}", u2, u2.getCountry());
            User u3 = entityManager.find(User.class, users.get(2).getId());
            log.info("!!!!! User: {} -> {}", u3, u3.getCountry());
        });
    }
}
