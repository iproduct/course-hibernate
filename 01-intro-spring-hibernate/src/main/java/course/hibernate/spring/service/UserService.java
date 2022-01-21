package course.hibernate.spring.service;

import course.hibernate.spring.dto.UserDetailsDto;
import course.hibernate.spring.entity.User;

import java.util.List;

public interface UserService {
    List<UserDetailsDto> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User create(User user);
    User update(User user);
    User deleteById(Long id);
    long count();
}
