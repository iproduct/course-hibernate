package course.hibernate.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Slf4j
public class DataInitJpaSpringBeans implements ApplicationRunner {
    @PersistenceContext
    private EntityManager em;

    private final ApplicationEventPublisher applicationEventPublisher;



    @Autowired
    public DataInitJpaSpringBeans(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

    }
}
