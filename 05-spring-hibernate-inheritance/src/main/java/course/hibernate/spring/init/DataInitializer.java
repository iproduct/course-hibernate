package course.hibernate.spring.init;

import course.hibernate.spring.entity.Role;
import course.hibernate.spring.entity.User;
import course.hibernate.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "Admin123&",
                    Set.of(Role.READER, Role.AUTHOR, Role.ADMIN)),
            new User("Default", "Author", "author", "Author123&",
                    Set.of(Role.READER, Role.AUTHOR))
    );
    private static final List<User> defaultUsers = List.of();

    private UserService userService;

    @Autowired
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userService.count() == 0) {
            List<User> users = SAMPLE_USERS.stream().map(userService::create).collect(Collectors.toList());
//            users.forEach(u -> u.setData(new UserData(u.getId(), "Java, Spring, Hibernate")));
            log.info("Creating users: {}",users);
        }

    }
}
