package course.hibernate.init;

import course.hibernate.entity.Account;
import course.hibernate.entity.AccountSummary;
import course.hibernate.entity.AccountTransaction;
import course.hibernate.entity.Client;
import course.hibernate.events.ContactCreationEvent;
import course.hibernate.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Slf4j
public class BankAccounsSubselectDemo implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    private final ApplicationEventPublisher applicationEventPublisher;
    // single TransactionTemplate shared amongst all methods in this instance
    private final TransactionTemplate transactionTemplate;
    // single PlatformTransactionManager shared amongst all methods in this instance
    private final PlatformTransactionManager transactionManager;

    private BankService bankService;


    @Autowired
    public BankAccounsSubselectDemo(
            ApplicationEventPublisher applicationEventPublisher,
            PlatformTransactionManager transactionManager,
            TransactionTemplate transactionTemplate,
            BankService bankService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.transactionTemplate = transactionTemplate;
        this.transactionManager = transactionManager;
        this.bankService = bankService;
    }

    @Override

    public void run(ApplicationArguments args) throws Exception {
        AccountSummary summary = bankService.openAcoount(7000*100);
        log.info("ACCOUNT SUMMARY for [{}] - balance: {}", summary.getClientName(), summary.getBalance() / 100.0);
        summary = bankService.updateAccount(summary.getId(), -1400 * 100);
        log.info("ACCOUNT SUMMARY after UPDATE for [{}] - balance: {}", summary.getClientName(), summary.getBalance() / 100.0);

    }



    @TransactionalEventListener
    public void handleUserCreatedTransactionCommit(ContactCreationEvent creationEvent) {
        log.info(">>> Transaction COMMIT for Contact: {}", creationEvent.getContact());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleUserCreatedTransactionRollaback(ContactCreationEvent creationEvent) {
        log.info(">>> Transaction ROLLBACK for Contact: {}", creationEvent.getContact());
    }

}
