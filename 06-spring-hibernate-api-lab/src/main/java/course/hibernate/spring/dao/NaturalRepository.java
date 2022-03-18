package course.hibernate.spring.dao;

import org.hibernate.LockOptions;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@NoRepositoryBean
public interface NaturalRepository<T, ID extends Serializable, NID extends Serializable> extends JpaRepository<T,ID> {
    Optional<T> findBySimpleNaturalId(NID naturalId, LockOptions options);
    Optional<T> findBySimpleNaturalId(NID naturalId);
    Optional<T> findByNaturalId(Map<String, Object> naturalIds);
}
