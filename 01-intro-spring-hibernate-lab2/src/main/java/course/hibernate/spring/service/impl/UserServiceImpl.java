package course.hibernate.spring.service.impl;

import course.hibernate.spring.dao.UserRepository;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.exception.EntityNotFoundException;
import course.hibernate.spring.exception.InvalindClientDataException;
import course.hibernate.spring.service.UserService;
import course.hibernate.spring.util.ExceptionHandlingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with ID=%s not found.")));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with username='%s' not found.")));
    }

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            throw new InvalindClientDataException("Invalid database constraint", ex,
                    ExceptionHandlingUtils.extractViolations(ex));
        }
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User deleteById(Long id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
