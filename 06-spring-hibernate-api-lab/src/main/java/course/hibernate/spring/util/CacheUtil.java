package course.hibernate.spring.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceUnit;
import java.util.List;

@Component
@Slf4j
public class CacheUtil {
    @PersistenceUnit
    SessionFactory sessionFactory;

    public void logCacheStatistics() {
        sessionFactory.getStatistics().logSummary();
        log.info("Second level caches:");
        List<String> secondLevelCaches = List.of(sessionFactory.getStatistics().getSecondLevelCacheRegionNames());
        System.out.println(secondLevelCaches);
        secondLevelCaches.forEach(name -> {
            try {
                log.info("{} -> {}", name, sessionFactory.getStatistics().getDomainDataRegionStatistics(name));
            } catch (Exception ex) {
                log.warn("No statistics for region '{}':", name);
            }
        });
    }
}
