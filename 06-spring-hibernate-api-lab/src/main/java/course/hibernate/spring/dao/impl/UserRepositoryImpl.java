package course.hibernate.spring.dao.impl;

import course.hibernate.spring.dao.UserRepository;
import course.hibernate.spring.dao.UserRepositoryCRUD;
import course.hibernate.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository("userRepo")
@NoRepositoryBean
@Transactional
public class UserRepositoryImpl extends NaturalIdRepositoryImpl<User, Long, String> implements UserRepositoryCRUD {
    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        super(new JpaMetamodelEntityInformation(User.class, entityManager.getMetamodel()), entityManager);
        this.entityManager = entityManager;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
       return entityManager.createQuery("select distinct u from User u left join fetch u.roles", User.class)
               .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
//        return Optional.ofNullable(entityManager.getReference(User.class, id)); // Lazy fetch
        return Optional.ofNullable(entityManager.find(User.class, id)); // Eager fetch
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return entityManager.createQuery("select u from User u left join fetch u.roles where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream().findFirst();
    }

    @Override
    public User save(User entity) {
        if(entity.getId() != null){
            return update(entity);
        }
        return create(entity);
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public long count() {
        return entityManager.createQuery("select count(u) from User u", Long.class)
                .getSingleResult();
    }
}
