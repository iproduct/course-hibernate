package course.hibernate.spring.dao;

import course.hibernate.spring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryJpa extends SimpleJpaRepository<User, Long> implements UserRepository {
    private EntityManager entityManager;

    @Autowired
    public UserRepositoryJpa(EntityManager em) {
        super(User.class, em);
        entityManager = em;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return entityManager.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream().findFirst();
    }

    @Override
    public User save(User entity) {
        if (entity.getId() != null) {
            return update(entity);
        } else {
            return create(entity);
        }
    }


    @Override
    public User create(User user) {
//        entityManager.persist(user);
        Long id = (Long) entityManager.unwrap(Session.class).save(user);
        log.info("Saved User ID = {}, {}", id, user);
        return user;
    }

    @Override
    public User update(User user) {
        User old = findById(user.getId()).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID='%s' does not exist.", user.getId())));
        log.info("!!! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n Updating user in UserRepository: {}", System.identityHashCode(user));
//        entityManager.unwrap(Session.class).evict(old);
//
//        old.setUsername(user.getUsername());
//        old.setFirstName(user.getFirstName());
//        old.setLastName(user.getLastName());
//        old.setRoles(user.getRoles());
//        old.setModified(user.getModified());

//        try {
        var result = (User) entityManager.unwrap(Session.class).merge(user);
//        } catch (Exception ex){
//            log.error("!!!Error during update", ex);
//            throw ex;
//        }
//        log.info("!!! Updated user: {} -> {}", System.identityHashCode(result), result);
        return result;
    }


    @Override
    public void deleteById(Long id) {
        User old = findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID='%s' does not exist.", id)));
        entityManager.remove(old); //.unwrap(Session.class).delete();
    }

    @Override
    public long count() {
        return entityManager.createQuery("select count(u) from User u", Long.class)
                .getSingleResult();
    }


}
