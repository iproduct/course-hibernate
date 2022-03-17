package course.hibernate.spring.dao;

import course.hibernate.spring.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepositoryCRUD extends UserRepository {
    User create(User user);
    User update(User user);
}


















