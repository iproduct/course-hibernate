package course.hibernate.spring.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.CacheRegionStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceUnit;
import java.util.List;

@Component
@Slf4j
public class CacheUtil {
    @PersistenceUnit
    SessionFactory sessionFactory;

    public void logCacheStatistics() {
        Statistics statistics = sessionFactory.getStatistics();
        statistics.logSummary();
        log.info("Second level caches:");
        List<String> secondLevelCaches = List.of(statistics.getSecondLevelCacheRegionNames());
        System.out.println(secondLevelCaches);
        secondLevelCaches.forEach(name -> {
            try {
//                log.info("{} -> {}", name, statistics.getDomainDataRegionStatistics(name));
                CacheRegionStatistics secondLevelCacheStatistics =
                        statistics.getDomainDataRegionStatistics(name);
                long hitCount = secondLevelCacheStatistics.getHitCount();
                long missCount = secondLevelCacheStatistics.getMissCount();
                double hitRatio = (double) hitCount / (hitCount + missCount);
                log.info("Region name: {} -> hitCount: {}, missCount: {}, ratio: {}", name, hitCount, missCount, hitRatio);
            } catch (Exception ex) {
                log.warn("No statistics for region '{}':", name);
            }
        });
    }
}
