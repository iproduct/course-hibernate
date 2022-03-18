package course.hibernate.spring.init;

import course.hibernate.spring.dao.UserRepository;
import course.hibernate.spring.dao.UserRepositoryNaturalId;
import course.hibernate.spring.entity.Book;
import course.hibernate.spring.entity.Person;
import course.hibernate.spring.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.annotations.*;
import org.hibernate.graph.EntityGraphs;
import org.hibernate.graph.GraphParser;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Component
@Slf4j
public class FetcingByNaturalId implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRepository userRepo;

    @Autowired
    private TransactionTemplate template;

    @Autowired
    CacheUtil cacheUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        template.executeWithoutResult(status -> {
            var admin = userRepo.findBySimpleNaturalId("admin");
            var author = userRepo.findBySimpleNaturalId("author");
            log.info(">>> User fetched by NATURAL ID: {}", admin);
            cacheUtil.logCacheStatistics();
        });
        template.executeWithoutResult(status -> {
            var admin = userRepo.findBySimpleNaturalId("admin");
            var author = userRepo.findBySimpleNaturalId("author");
            log.info(">>> User fetched in SECOND transaction by NATURAL ID: {}", admin);
            cacheUtil.logCacheStatistics();
        });

    }
}
