package course.hibernate.spring.init;

import course.hibernate.spring.dao.UserRepository;
import course.hibernate.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

public class DataInitializer implements ApplicationRunner {
    private static final List<User> defaultUsers = List.of();

    @Autowired
    private UserRepository userRepo;


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
