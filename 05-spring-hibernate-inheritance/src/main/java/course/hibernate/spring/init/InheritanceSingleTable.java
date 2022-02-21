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
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InheritanceSingleTable implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            DebitAccount debitAccount =
                    new DebitAccount(2001L, "John Doe", BigDecimal.valueOf(100),
                            BigDecimal.valueOf(1.5), BigDecimal.valueOf(15));
            CreditAccount creditAccount =
                    new CreditAccount(3001L, "John Doe", BigDecimal.valueOf(1000),
                            BigDecimal.valueOf(1.9), BigDecimal.valueOf(5000));
            entityManager.persist(debitAccount);
            entityManager.persist(creditAccount);
            Account otherAccount =
                    new Account(5001L, "Hristo Petrov", BigDecimal.valueOf(1000),
                            BigDecimal.valueOf(1.9));
            entityManager.persist(debitAccount);
            entityManager.persist(creditAccount);
            entityManager.persist(otherAccount);
        });

        template.executeWithoutResult(status -> {
            log.info("!!!!! Fetching All ACCOUNTS:");
            List<Account> accounts = entityManager.createQuery("select a from Account a")
                    .getResultList();
            accounts.forEach(a -> log.info("Account: {}", a));
        });
    }
}
