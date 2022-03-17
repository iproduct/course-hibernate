package course.hibernate.spring;

import course.hibernate.spring.dao.impl.NaturalIdRepositoryImpl;
import course.hibernate.spring.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NaturalIdRepositoryImpl.class)
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

}
