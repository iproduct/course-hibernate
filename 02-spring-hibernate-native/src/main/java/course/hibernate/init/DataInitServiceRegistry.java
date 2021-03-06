package course.hibernate.init;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import course.hibernate.config.MyOrgPhysicalNamingStrategy;
import course.hibernate.entity.Contact;
import course.hibernate.entity.Gender;
import course.hibernate.entity.Name;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.integrator.spi.Integrator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.sql.DataSource;
import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@Component
@Slf4j
public class DataInitServiceRegistry implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Build BootstrapServiceRegistryBuilder
        final BootstrapServiceRegistryBuilder bsrb = new BootstrapServiceRegistryBuilder()
                .enableAutoClose();

        // Apply integrations if any
        Integrator integrator = ingegrator();
        if (integrator != null) {
            bsrb.applyIntegrator(integrator);
        }

        // Build BootstrapServiceRegistry
        final BootstrapServiceRegistry bsr = bsrb.build();

        //Build StandardServiceRegistry
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder(bsr)
                .applySettings(properties())
//                .configure()
                .build();

        //Add Metadata Sources
        MetadataSources mds = new MetadataSources(ssr);
        mds.addPackage("course.hibernate.entity");

        // OR using  fluent-hibernate EntityScanner: https://github.com/v-ladynev/fluent-hibernate/blob/master/fluent-hibernate-core/src/main/java/com/github/fluent/hibernate/cfg/scanner/EntityScanner.java
//        List<Class<?>> classes = EntityScanner
//                .scanPackages("my.com.entities", "my.com.other.entities").result();
////        MetadataSources metadataSources = new MetadataSources();
//        for (Class<?> annotatedClass : classes) {
//            metadataSources.addAnnotatedClass(annotatedClass);
//        }
////        SessionFactory sessionFactory = metadataSources.buildMetadata()
//                .buildSessionFactory();
        for(Class<?> entityCls : getEntityClasses("course.hibernate.entity")) {
            log.info("Adding annotated class: {}", entityCls.getSimpleName());
            mds.addAnnotatedClass(entityCls);
        }

        // Create MetadataBuilder
        MetadataBuilder mdb = mds.getMetadataBuilder()
                .enableNewIdentifierGeneratorSupport(true)
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .applyPhysicalNamingStrategy(getPhisicalNamingStrategy());
        Metadata metadata = mdb.build();

        // Get SessionFactory
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        // Get Session
        Session session = sessionFactory.openSession();


        // Persist entity
        Contact contact = new Contact(1,
                new Name("Ivan", "Dimitrov", "Petrov"), Gender.MALE,
                "From work", new URL("http://ivan.petrov.me/"), true);
        session.beginTransaction();
        session.persist(contact);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    private PhysicalNamingStrategy getPhisicalNamingStrategy() {
        return new MyOrgPhysicalNamingStrategy();
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

    public Integrator ingegrator() {
        return null;
    }

    protected DataSource dataSource() {
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

//    public DataSource mysqlDataSource() {
////        try {
//        MysqlDataSource dataSource = new MysqlDataSource();
//
//        String url = "jdbc:mysql://localhost/hibernate_native?createDatabaseIfNotExist=true&useSSL=false";
//
//        if (!MySQL8Dialect.class.isAssignableFrom(MySQL8Dialect.class)) {
//            url +=
////                        "&useTimezone=" + useTimezone +
////                        "&useJDBCCompliantTimezoneShift=" + useJDBCCompliantTimezoneShift +
//                    "&useLegacyDatetimeCode=true";
//        }
//
//        dataSource.setURL(url);
//        dataSource.setUser("root");
//        dataSource.setPassword("root");
//
////            dataSource.setRewriteBatchedStatements(true);
////            dataSource.setUseCursorFetch(useCursorFetch);
////            dataSource.setCachePrepStmts(cachePrepStmts);
////            dataSource.setUseServerPrepStmts(useServerPrepStmts);
////            if (prepStmtCacheSqlLimit != null) {
////                dataSource.setPrepStmtCacheSqlLimit(prepStmtCacheSqlLimit);
////            }
//
//        return dataSource;
////        } catch (SQLException e) {
////            throw new IllegalArgumentException(e);
////        }
//    }

    private Collection<Class> getEntityClasses(final String pack) {
        final StandardJavaFileManager fileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null);
        try {
            return StreamSupport.stream(fileManager.list(StandardLocation.CLASS_PATH, pack, Collections.singleton(JavaFileObject.Kind.CLASS), false).spliterator(), false)
                    .map(FileObject::getName)
                    .map(name -> {
                        try {
                            final String[] split = name
                                    .replace(".class", "")
                                    .replace(")", "")
                                    .split(Pattern.quote(File.separator));

                            final String fullClassName = pack + "." + split[split.length - 1];
                            return Class.forName(fullClassName);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                    })
                    .filter(aClass -> aClass.isAnnotationPresent(Entity.class))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
