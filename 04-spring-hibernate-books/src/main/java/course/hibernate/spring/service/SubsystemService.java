package course.hibernate.spring.service;

import course.hibernate.spring.entity.Subsystem;
import course.hibernate.spring.entity.SystemUser;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class SubsystemService {
    @PersistenceContext
    private EntityManager em;

    public Subsystem createSubsystem(Subsystem ss) {
        em.persist(ss);
        return ss;
    }
    public SystemUser createUser(SystemUser su) {
        em.unwrap(Session.class).save(su);
        return su;
    }
}
















