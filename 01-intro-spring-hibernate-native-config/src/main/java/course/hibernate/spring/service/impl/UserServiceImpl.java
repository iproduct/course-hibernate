package course.hibernate.spring.service.impl;

import course.hibernate.spring.dao.UserRepository;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.exception.EntityNotFoundException;
import course.hibernate.spring.exception.InvalidClientDataException;
import course.hibernate.spring.service.UserService;
import course.hibernate.spring.util.ExceptionHandlingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            throw new InvalidClientDataException("Database constraint invalidated", ex,
                    ExceptionHandlingUtils.extractViolations(ex));
        }
    }

    @Override
    public User update(User user) {
        User old = findById(user.getId());
        if(!old.getUsername().equals(user.getUsername())) {
            throw new InvalidClientDataException("Username can not be changed");
        }
        user.setModified(LocalDateTime.now());
        try {
            return userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            throw new InvalidClientDataException("Database constraint invalidated", ex,
                    ExceptionHandlingUtils.extractViolations(ex));
        }
    }

    @Override
    public User deleteById(Long id) {
        User old = findById(id);
        userRepository.deleteById(id);
        return old;
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
