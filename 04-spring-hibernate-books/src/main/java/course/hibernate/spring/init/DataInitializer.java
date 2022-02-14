package course.hibernate.spring.init;

import course.hibernate.spring.dao.BookRepository;
import course.hibernate.spring.dao.UserRepository;
import course.hibernate.spring.entity.*;
import course.hibernate.spring.service.SubsystemService;
import course.hibernate.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
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
    private BookRepository bookRepo;
    private SubsystemService subsystemService;

    @Autowired
    public DataInitializer(UserService userService, BookRepository bookRepo, SubsystemService subsystemService) {
        this.userService = userService;
        this.bookRepo = bookRepo;
        this.subsystemService = subsystemService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userService.count() == 0) {
            log.info("Creating users: {}",
                    SAMPLE_USERS.stream().map(userService::create).collect(Collectors.toList()));
        }

        // books demo
        Book b1 = new Book("Effective Java", List.of(new Author("Joshua", "Bloch",
                LocalDate.of(1965, 8, 11))), "978-0134685991");
        Book b2 = new Book("Thinking in Java", List.of(new Author("Bruce", "Eckel",
                LocalDate.of(1965, 8, 19))), " 978-0131872486");
        bookRepo.saveAll(List.of(b1, b2));
        for(Book book: bookRepo.findAll()){
            System.out.println(book);
        }


        // subsystem user demo
        Subsystem ss1 = subsystemService.createSubsystem(
                new Subsystem("Internal_Projects", "Internal project management subsystem"));
        log.info("Created Subsystem: {}", ss1);
        SystemUser su1 = subsystemService.createUser(
                new SystemUser(new PK(ss1, "john"), "John Doe"));
        log.info("Created Subsystem User: {}", su1);
        log.info("Finding User by ID='{}': {}", su1.getId(), subsystemService.findUserById(su1.getId()));
        log.info("Finding Susbsystem by ID='{}': {}", ss1.getId(), subsystemService.findSubsystemById(ss1.getId()));
    }
}
