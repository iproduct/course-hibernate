package course.hibernate.init;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import course.hibernate.entity.Contact;
import course.hibernate.entity.Gender;
import course.hibernate.entity.Name;
import course.hibernate.utils.GenderConverter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.integrator.spi.Integrator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
@Slf4j
public class DataInitServiceRegistry implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // Build BootstrapServiceRegistryBuilder
        BootstrapServiceRegistryBuilder bsrb = new BootstrapServiceRegistryBuilder().enableAutoClose();

        // Apply integrations if any
        Integrator integrator = integrator();
        if (integrator != null) {
            bsrb.applyIntegrator(integrator);
        }

        // Build BootstrapServiceRegistry
        BootstrapServiceRegistry bsr = bsrb.build();

        // Build StandardServiceRegistry
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder(bsr)
                .applySettings(properties())
//                .configure()
                .build();

        // Add Metadata sources
        MetadataSources mds = new MetadataSources(ssr);
        mds.addPackage("course.hibernate.entity");
        mds.addAnnotatedClass(Contact.class);
        // mds.addResource("");

        // Build Metadata
        Metadata metadata = mds.getMetadataBuilder()
                .enableNewIdentifierGeneratorSupport(true)
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
//                .applyPhysicalNamingStrategy(getPhysicalNamingStrategy())
                .applyAttributeConverter( new GenderConverter())
                .build();

        // Create SessionFactory
        // Create Session
        try (
                SessionFactory sf = metadata.buildSessionFactory();
                Session session = sf.openSession()) {

            // Persist Entity
            List<Contact> contacts= List.of(
                new Contact(1,
                    new Name("Ivan", "Dimitrov", "Petrov"), Gender.MALE,
                    "From work", new URL("http://ivan.petrov.me/"), true),
                new Contact(2,
                    new Name("Maria", "Dimitrova", "Hristova"), Gender.FEMALE,
                    "Friend contact", new URL("http://maria.dimitrova.me/"), true));

            session.beginTransaction();
            contacts.forEach(contact -> {
                log.info("Creating Contacts:{} - {}", contact.getId(), contact);
                session.persist(contact);
            });
            session.getTransaction().commit();
        }
    }

    private Map properties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        //log settings
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.use_sql_comments", true);
        properties.put("hibernate.show_sql", true);
        //data source settings
        DataSource dataSource = dataSource();
        if (dataSource != null) {
            properties.put("hibernate.connection.datasource", dataSource);
        }
        properties.put("hibernate.generate_statistics", Boolean.TRUE.toString());

//        properties.put("net.sf.ehcache.configurationResourceName",
//                Thread.currentThread().getContextClassLoader().getResource("ehcache.xml").toString());
        return properties;
    }

    private DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(mysqlDataSource());
        HikariDataSource poolingDataSource = new HikariDataSource(hikariConfig);
        int cpuCores = Runtime.getRuntime().availableProcessors();
        poolingDataSource.setMaximumPoolSize(cpuCores * 4);
//        poolingDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        poolingDataSource.setJdbcUrl("jdbc:mysql://localhost/hibernate_native2?createDatabaseIfNotExist=true&useSSL=false");
//        poolingDataSource.setUsername("root");
//        poolingDataSource.setPassword("root");
        return poolingDataSource;
    }

    public DataSource mysqlDataSource() {
//        try {
        MysqlDataSource dataSource = new MysqlDataSource();

        String url = "jdbc:mysql://localhost/hibernate_native2?createDatabaseIfNotExist=true&useSSL=false";

        if (!MySQL8Dialect.class.isAssignableFrom(MySQL8Dialect.class)) {
            url +=
//                        "&useTimezone=" + useTimezone +
//                        "&useJDBCCompliantTimezoneShift=" + useJDBCCompliantTimezoneShift +
                    "&useLegacyDatetimeCode=true";
        }

        dataSource.setURL(url);
        dataSource.setUser("root");
        dataSource.setPassword("root");

//            dataSource.setRewriteBatchedStatements(true);
//            dataSource.setUseCursorFetch(useCursorFetch);
//            dataSource.setCachePrepStmts(cachePrepStmts);
//            dataSource.setUseServerPrepStmts(useServerPrepStmts);
//            if (prepStmtCacheSqlLimit != null) {
//                dataSource.setPrepStmtCacheSqlLimit(prepStmtCacheSqlLimit);
//            }

        return dataSource;
//        } catch (SQLException e) {
//            throw new IllegalArgumentException(e);
//        }
    }

    private PhysicalNamingStrategy getPhysicalNamingStrategy() {
        return null;
    }

    public Integrator integrator() {
        return null;
    }
}
