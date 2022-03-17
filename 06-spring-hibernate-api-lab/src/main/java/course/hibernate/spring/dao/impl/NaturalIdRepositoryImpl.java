package course.hibernate.spring.dao.impl;

import course.hibernate.spring.dao.NaturalRepository;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Transactional(readOnly = true)
public class NaturalIdRepositoryImpl<T, ID extends Serializable, NID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements NaturalRepository<T, ID, NID> {
    private EntityManager entityManager;

    public NaturalIdRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findBySimpleNaturalId(NID naturalId) {
        return entityManager.unwrap(Session.class)
                .bySimpleNaturalId(this.getDomainClass())
                .loadOptional(naturalId);
    }

    @Override
    public Optional<T> findByNaturalId(Map<String, Object> naturalIds) {
        var loadAccess = entityManager.unwrap(Session.class)
                .byNaturalId(this.getDomainClass());
        naturalIds.forEach(loadAccess::using);
        return loadAccess.loadOptional();
    }
}
