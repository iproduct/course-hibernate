package course.hibernate.spring.dao;

import course.hibernate.spring.dto.UserDetailsDto;
import course.hibernate.spring.dto.UserDetailsInterface;
import course.hibernate.spring.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDetailsDtoRepository extends JpaRepository<User, Long> {
//    @EntityGraph(value = "User.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT NEW  course.hibernate.spring.dto.UserDetailsDto(u.id, u.firstName, u.lastName, u.username, GROUP_CONCAT(r)) " +
            "FROM User u LEFT JOIN u.roles r GROUP BY u.id")
    List<UserDetailsDto> findAllBy();
    Optional<User> findByUsername(String username);
}
