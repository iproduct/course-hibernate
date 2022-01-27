package course.hibernate.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("course.hibernate.entity")
public class JpaConfig {
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        int cpuCores = Runtime.getRuntime().availableProcessors();
        hikariConfig.setMaximumPoolSize(cpuCores * 4);
//        hikariConfig.setDataSource(mysqlDataSource());
//        HikariDataSource poolingDataSource = new HikariDataSource(hikariConfig);
        HikariDataSource poolingDataSource = new HikariDataSource();
        poolingDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        poolingDataSource.setJdbcUrl("jdbc:mysql://localhost/hibernate_native?createDatabaseIfNotExist=true&useSSL=false");
        poolingDataSource.setUsername("root");
        poolingDataSource.setPassword("root");
        return poolingDataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan("course.hibernate.entity");

        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.format_sql", Boolean.TRUE.toString());
        hibernateProperties.setProperty("hibernate.use_sql_comments", Boolean.TRUE.toString());
        hibernateProperties.setProperty("hibernate.show_sql", Boolean.TRUE.toString());
        hibernateProperties.setProperty("hibernate.generate_statistics", Boolean.TRUE.toString());

        return hibernateProperties;
    }
}
