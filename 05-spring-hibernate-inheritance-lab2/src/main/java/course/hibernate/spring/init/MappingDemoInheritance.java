package course.hibernate.spring.init;

import course.hibernate.spring.entity.*;
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

import static course.hibernate.spring.entity.Role.*;

@Component
@Slf4j
public class MappingDemoInheritance implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
        });

//        template.executeWithoutResult(status -> {
//            List<User> fetchedUsers = entityManager.createQuery("SELECT u FROM User u")
//                    .getResultList();
//            for(User u : fetchedUsers) {
//                log.info("!!!!! User: {} -> Country: {}", u, u.getCountry());
//            }
//        });
    }
}
