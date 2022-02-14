package course.hibernate.spring.service;

import course.hibernate.spring.dto.SystemUserWithDetails;
import course.hibernate.spring.entity.PK;
import course.hibernate.spring.entity.Subsystem;
import course.hibernate.spring.entity.SystemUser;
import course.hibernate.spring.entity.SystemUserDetails;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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
        su.getId().getSubsystem().getSystemUsers().add(su);
        em.unwrap(Session.class).save(su);
        return su;
    }

    public SystemUser createUserWithDetails(SystemUser su, String qualifications) {
        su.getId().getSubsystem().getSystemUsers().add(su);
        em.unwrap(Session.class).save(su);
        SystemUserDetails details = new SystemUserDetails(su, qualifications);
        em.persist(details);
        return su;
    }

    public SystemUserWithDetails findUserWithDetails(PK userId) {
        SystemUserWithDetails suwd = em.createQuery("SELECT NEW course.hibernate.spring.dto.SystemUserWithDetails(su.id.subsystem.id, su.id.username, su.name, sud.qualifications)  " +
                        "FROM SystemUser su LEFT JOIN FETCH SystemUserDetails sud ON su.id = sud.user " +
                        "WHERE su.id = :userId", SystemUserWithDetails.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return suwd;
    }

    public Optional<Subsystem> findSubsystemById(String id) {
        return Optional.ofNullable(em.find(Subsystem.class, id));
    }

    public Optional<SystemUser> findUserById(PK id) {
        return Optional.ofNullable(em.find(SystemUser.class, id));
    }
}
















