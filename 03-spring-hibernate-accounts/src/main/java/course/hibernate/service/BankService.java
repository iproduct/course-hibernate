package course.hibernate.service;

import course.hibernate.entity.Account;
import course.hibernate.entity.AccountSummary;
import course.hibernate.entity.AccountTransaction;
import course.hibernate.entity.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class BankService {
    @PersistenceContext
    private EntityManager entityManager;

    public AccountSummary openAcoount(int amountCents) {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        entityManager.persist(client);

        Account account = new Account();
        account.setId(1L);
        account.setClient(client);
        account.setDescription("Checking account");
        entityManager.persist(account);

        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccount(account);
        transaction.setDescription("Salary");
        transaction.setCents(amountCents);
        entityManager.persist(transaction);

        AccountSummary summary = entityManager.createQuery(
                        "select s " +
                                "from AccountSummary s " +
                                "where s.id = :id", AccountSummary.class)
                .setParameter("id", account.getId())
                .getSingleResult();
        return summary;
    }

    public AccountSummary updateAccount(Long accountId, int amountCents) {
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccount(entityManager.getReference(Account.class, 1L));
        transaction.setDescription("Shopping");
        transaction.setCents(amountCents);
        entityManager.persist(transaction);
        entityManager.flush();

        AccountSummary summary = entityManager.find(AccountSummary.class, accountId);
        entityManager.refresh(summary);
        return summary;
    }

}
